package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.entities.Forum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Post(
    val forum: Forum,
    val mission: CampaignMission
)

class ForumViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            val forums = mutableListOf<Forum>()
            val missions = mutableMapOf<String, CampaignMission>()

            firestore.collection("forums")
                .orderBy("time", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { forumResult ->
                    forums.addAll(forumResult.toObjects(Forum::class.java))

                    firestore.collection("campaign_missions")
                        .get()
                        .addOnSuccessListener { missionResult ->
                            missionResult.forEach { document ->
                                missions[document.id] = document.toObject(CampaignMission::class.java).copy(id = document.id)
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
