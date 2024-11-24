package com.papb.tolonginprojectpapb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.screens.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForumViewModel : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())

    val posts: StateFlow<List<Post>> get() = _posts

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val initialPosts = listOf(
                Post(
                    userName = "Julaiha",
                    time = "15 menit yang lalu",
                    content = "Saya baru saja menyelesaikan misi harian Hemat Energi dan mendapatkan 20 poin tambahan! Ikuti misi yang sama dengan saya untuk mendapatkan reward yang sama!",
                    cardTitle = "Hemat Energi",
                    cardDescription = "",
                    cardSubtitle = "Lingkungan",
                    cardXP = "+20 XP",
                    likes = 20,
                    comments = 15,
                    profileImageResId = R.drawable.ic_profile, // Profile image
                    cardIllustrationResId = R.drawable.ic_card_illustration_1 // Illustration 1
                ),
                Post(
                    userName = "Afkarroar",
                    time = "15 menit yang lalu",
                    content = "Fyuuh.. akhirnya kelar juga satu misi besar",
                    cardTitle = "Tanam Pohon",
                    cardDescription = "Membantu menyerap karbon dioksida, meningkatkan kualitas udara.",
                    cardSubtitle = "Lingkungan",
                    cardXP = "+200 XP",
                    likes = 100,
                    comments = 15,
                    profileImageResId = R.drawable.ic_profile2, // Profile image
                    cardIllustrationResId = R.drawable.ic_card_illustration_2 // Illustration 2
                ),
                Post(
                    userName = "Rheawuirz",
                    time = "15 menit yang lalu",
                    content = "Saya baru saja menyelesaikan misi harian Jalan Kaki dan mendapatkan 20 poin tambahan! Ikuti misi yang sama dengan saya untuk mendapatkan reward yang sama!",
                    cardTitle = "Jalan Kaki",
                    cardDescription = "",
                    cardSubtitle = "Travel",
                    cardXP = "+50 XP",
                    likes = 20,
                    comments = 15,
                    profileImageResId = R.drawable.ic_profile3, // Profile image
                    cardIllustrationResId = R.drawable.ic_card_illustration_3 // Illustration 3
                )
            )
            _posts.value = initialPosts
        }
    }



    fun addPost(newPost: Post) {
        viewModelScope.launch {
            val currentPosts = _posts.value.toMutableList()
            currentPosts.add(0, newPost)
            _posts.value = currentPosts
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            val currentPosts = _posts.value.toMutableList()
            currentPosts.remove(post)
            _posts.value = currentPosts
        }
    }
}