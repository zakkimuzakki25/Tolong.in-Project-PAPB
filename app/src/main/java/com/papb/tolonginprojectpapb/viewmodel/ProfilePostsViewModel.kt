package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.papb.tolonginprojectpapb.entities.UserPost

class ProfilePostsViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<UserPost>>()
    val posts: LiveData<List<UserPost>> = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        _posts.value = listOf(
            UserPost(
                username = "@afkaarroar",
                avatarUrl = "",
                timestamp = "0 minutes ago",
                content = "Saya baru saja menyelesaikan misi harian Hemat Energi dan mendapatkan 20 XP!",
                xp = 20,
                missionName = "Hemat Energi",
                missionCategory = "Lingkungan"
            ),
            UserPost(
                username = "@afkaarroar",
                avatarUrl = "",
                timestamp = "0 minutes ago",
                content = "Fyuuh.. akhirnya kelar juga satu misi besar",
                xp = 200,
                missionName = "Tanam Pohon",
                missionCategory = "Lingkungan"
            )
        )
    }
}
