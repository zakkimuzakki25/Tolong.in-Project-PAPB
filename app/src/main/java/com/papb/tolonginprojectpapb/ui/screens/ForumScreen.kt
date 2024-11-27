package com.papb.tolonginprojectpapb.ui.screens

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.activities.CreateForumActivity
import com.papb.tolonginprojectpapb.ui.components.cards.PostCard
import com.papb.tolonginprojectpapb.ui.components.headers.NonBackHeader
import com.papb.tolonginprojectpapb.ui.theme.Primary200
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.viewmodel.ForumViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForumScreen(viewModel: ForumViewModel) {
    val posts by viewModel.posts.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            viewModel.loadPosts()
        }
    }

    Scaffold(
        topBar = {
            NonBackHeader(
                "Forum"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    launcher.launch(Intent(context, CreateForumActivity::class.java))
                },
                containerColor = Primary500,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(50))
            ) {
                Icon(
                    painter = rememberAsyncImagePainter(R.drawable.ic_add_forum),
                    tint = Color.White,
                    contentDescription = "Tambah Forum",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 8.dp, vertical = 0.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            posts.forEach { post ->
                PostCard(forum = post.forum, mission = post.mission)
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Primary200
                )
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}
