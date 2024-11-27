package com.papb.tolonginprojectpapb.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.Forum
import com.papb.tolonginprojectpapb.entities.RecentActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class CreateForumViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userInput = MutableStateFlow("")
    val userInput: StateFlow<String> = _userInput

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _recentActivities = MutableStateFlow<List<RecentActivity>>(emptyList())
    val recentActivities: StateFlow<List<RecentActivity>> = _recentActivities.asStateFlow()

    private val userId = auth.currentUser?.uid ?: ""

    init {
        fetchRecentActivities()
    }

    fun onUserInputChange(input: String) {
        _userInput.value = input
    }

    fun submitActivity(selectedActivityId: String) {
        if (userInput.value.isEmpty() || selectedActivityId.isEmpty()) return

        _isLoading.value = true

        // Fetch user details for username and avatar_url
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { userDoc ->
                val username = userDoc.getString("username") ?: "Anonymous"
                val avatarUrl = userDoc.getString("avatar_url") ?: ""

                val forumData = Forum(
                    username = username,
                    avatar_url = avatarUrl,
                    time = Timestamp.now().toDate().toString(), // Use timestamp for time
                    caption = userInput.value,
                    likes = 0,
                    comments = 0,
                    mission_id = selectedActivityId
                )

                // Submit forum data to Firestore
                firestore.collection("forums")
                    .add(forumData)
                    .addOnSuccessListener {
                        _isLoading.value = false
                    }
                    .addOnFailureListener { e ->
                        println("Failed to submit forum: ${e.message}")
                        _isLoading.value = false
                    }
            }
            .addOnFailureListener { e ->
                println("Failed to fetch user details: ${e.message}")
                _isLoading.value = false
            }
    }

    private fun fetchRecentActivities() {
        viewModelScope.launch {
            val recentActivities = mutableListOf<RecentActivity>()

            // Fetch recent missions
            firestore.collection("users_missions")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnSuccessListener { missionSnapshot ->
                    missionSnapshot.documents.forEach { doc ->
                        val missionId = doc.getString("mission_id") ?: return@forEach
                        val timestamp = doc.getLong("timestamp") ?: 0L

                        firestore.collection("campaign_missions").document(missionId)
                            .get()
                            .addOnSuccessListener { missionDoc ->
                                val missionTitle = missionDoc.getString("title") ?: "Unknown Mission"
                                val missionCategory = missionDoc.getString("category") ?: "Mission"
                                val missionImage = missionDoc.getString("illustration_url") ?: ""
                                val missionXP = missionDoc.getLong("plus_xp") ?: 0

                                recentActivities.add(
                                    RecentActivity(
                                        id = missionId,
                                        title = missionTitle,
                                        timestamp = timestamp,
                                        category = missionCategory,
                                        imageUrl = missionImage,
                                        xp = missionXP
                                    )
                                )

                                _recentActivities.value = recentActivities.sortedByDescending { it.timestamp }
                            }
                    }
                }
        }
    }
}
