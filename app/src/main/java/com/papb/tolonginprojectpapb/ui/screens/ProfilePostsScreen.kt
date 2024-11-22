package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.entities.UserPost
import com.papb.tolonginprojectpapb.viewmodel.ProfilePostsViewModel

@Composable
fun ProfilePostsScreen(viewModel: ProfilePostsViewModel = viewModel()) {
    val posts by viewModel.posts.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts.size) { index ->
            PostCard(post = posts[index])
        }
    }
}

@Composable
fun PostCard(post: UserPost) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.avatar_placeholder),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = post.username, style = MaterialTheme.typography.bodyMedium)
                    Text(text = post.timestamp, style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Options")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = post.content, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.mission_placeholder),
                    contentDescription = "Mission Image",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = post.missionName, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = post.missionCategory,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(text = "XP: ${post.xp}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
