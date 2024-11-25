package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.entities.Forum
import com.papb.tolonginprojectpapb.entities.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _avatarUrl = MutableLiveData<String>()
    val avatarUrl: LiveData<String> = _avatarUrl

    private val _xp = MutableLiveData<Int>()
    val xp: LiveData<Int> = _xp

    private val _carbonData = MutableLiveData<Pair<Double, Double>>()
    val carbonData: LiveData<Pair<Double, Double>> = _carbonData

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        fetchUserData()
        fetchCarbonData()
        loadProfilePosts()
    }

    // Fetch user data from Firestore
    private fun fetchUserData() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    user?.let {
                        _name.value = it.fullname
                        _phoneNumber.value = it.phone_number
                        _email.value = it.email
                        _avatarUrl.value = it.avatar_url
                        _xp.value = it.xp

                        _user.value = it
                    }
                }
            }
            .addOnFailureListener { e ->
                println("Failed to fetch user data: ${e.message}")
            }
    }

    // Fetch carbon data (can be updated to fetch from Firestore if needed)
    private fun fetchCarbonData() {
        // Example placeholder, replace with database fetching if necessary
        _carbonData.value = Pair(30.59, 780.0)
    }

    // Load profile posts
    private fun loadProfilePosts() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val forums = mutableListOf<Forum>()
            val missions = mutableMapOf<String, CampaignMission>()

            firestore.collection("forums")
                .whereEqualTo("user_id", userId)
                .get()
                .addOnSuccessListener { forumResult ->
                    forums.addAll(forumResult.toObjects(Forum::class.java))

                    firestore.collection("campaign_missions")
                        .get()
                        .addOnSuccessListener { missionResult ->
                            missionResult.forEach { document ->
                                missions[document.id] = document.toObject(CampaignMission::class.java)
                            }

                            val combinedPosts = forums.mapNotNull { forum ->
                                val mission = missions[forum.mission_id]
                                if (mission != null) {
                                    Post(forum, mission)
                                } else {
                                    null
                                }
                            }

                            _posts.value = combinedPosts
                        }
                        .addOnFailureListener { e ->
                            println("Failed to fetch missions: ${e.message}")
                        }
                }
                .addOnFailureListener { e ->
                    println("Failed to fetch forums: ${e.message}")
                }
        }
    }

    // Update user profile
    fun updateProfile(newName: String, newPhoneNumber: String, newEmail: String) {
        val userId = auth.currentUser?.uid ?: return
        val updatedData = mapOf(
            "fullname" to newName,
            "phone_number" to newPhoneNumber,
            "email" to newEmail
        )

        firestore.collection("users")
            .document(userId)
            .update(updatedData)
            .addOnSuccessListener {
                _name.value = newName
                _phoneNumber.value = newPhoneNumber
                _email.value = newEmail
            }
            .addOnFailureListener { e ->
                println("Failed to update profile: ${e.message}")
            }
    }
}
