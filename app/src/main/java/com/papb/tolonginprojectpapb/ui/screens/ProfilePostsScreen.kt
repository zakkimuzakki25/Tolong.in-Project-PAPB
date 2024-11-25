package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.papb.tolonginprojectpapb.ui.components.cards.PostCard
import com.papb.tolonginprojectpapb.viewmodel.ProfilePostsViewModel
import com.papb.tolonginprojectpapb.viewmodel.ProfileViewModel

@Composable
fun ProfilePostsScreen(viewModel: ProfileViewModel) {
    val posts by viewModel.posts.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts.size) { index ->
            PostCard(forum = posts[index].forum, mission = posts[index].mission)
        }
    }
}
