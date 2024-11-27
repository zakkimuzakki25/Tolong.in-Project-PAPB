package com.papb.tolonginprojectpapb.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.papb.tolonginprojectpapb.ui.components.cards.PostCard
import com.papb.tolonginprojectpapb.ui.theme.Primary200
import com.papb.tolonginprojectpapb.viewmodel.ProfilePostsViewModel
import com.papb.tolonginprojectpapb.viewmodel.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfilePostsScreen(viewModel: ProfileViewModel) {
    val posts by viewModel.posts.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize().padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts.size) { index ->
            val post = posts[index]
            PostCard(forum = post.forum, mission = post.mission)
            HorizontalDivider(
                thickness = 1.dp,
                color = Primary200
            )
        }
    }
}

