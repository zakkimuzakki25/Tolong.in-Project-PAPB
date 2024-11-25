package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.entities.Forum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfilePostsViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        loadProfilePosts()
    }

    private fun loadProfilePosts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
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
                }
        }
    }
}
