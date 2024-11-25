package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.entities.Forum
import com.papb.tolonginprojectpapb.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.*

class CreateForumViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _currentUser = MutableStateFlow(User(username = "", avatar_url = ""))
    val currentUser: StateFlow<User> get() = _currentUser

    private val _recentActivities = MutableStateFlow<List<CampaignMission>>(emptyList())
    val recentActivities: StateFlow<List<CampaignMission>> get() = _recentActivities

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchUserDetails()
        fetchRecentActivities()
    }

    private fun fetchUserDetails() {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val username = document.getString("username") ?: ""
                    val avatarUrl = document.getString("avatar_url") ?: ""
                    _currentUser.value = User(username, avatarUrl)
                }
        }
    }

    private fun fetchRecentActivities() {
        auth.currentUser?.uid?.let { userId ->
            db.collection("users_missions")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnSuccessListener { result ->
                    val activities = result.documents.mapNotNull { document ->
                        document.toObject(CampaignMission::class.java)
                    }
                    _recentActivities.value = activities
                }
        }
    }

    fun createForum(caption: String, missionId: String?) {
        if (missionId == null) return

        _isLoading.value = true

        auth.currentUser?.uid?.let { userId ->
            val username = currentUser.value.username
            val avatarUrl = currentUser.value.avatar_url
            val time = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date())

            val newForum = Forum(
                username = username,
                avatar_url = avatarUrl,
                time = time,
                caption = caption,
                likes = 0,
                comments = 0,
                mission_id = missionId
            )

            db.collection("forums").add(newForum)
                .addOnSuccessListener {
                    _isLoading.value = false
                }
                .addOnFailureListener {
                    _isLoading.value = false
                }
        }
    }
}
