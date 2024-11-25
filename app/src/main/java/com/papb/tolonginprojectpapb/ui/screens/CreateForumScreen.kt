package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.Secondary500
import com.papb.tolonginprojectpapb.viewmodel.CreateForumViewModel

@Composable
fun CreateForumScreen(
    viewModel: CreateForumViewModel,
    onForumCreated: () -> Unit
) {
    val user by viewModel.currentUser.collectAsState()
    val recentActivities by viewModel.recentActivities.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var caption by remember { mutableStateOf("") }
    var selectedMissionId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            BackHeader(
                title = "Unggah Kegiatan"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = user.avatar_url,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Gray, RoundedCornerShape(50)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = user.username,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "@${user.username}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    label = { Text("Ceritakan Kegiatanmu! *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Kegiatan Terakhir",
                    style = MaterialTheme.typography.titleMedium
                )

                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recentActivities) { activity ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clickable { selectedMissionId = activity.id },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedMissionId == activity.id) Primary500.copy(alpha = 0.1f) else Color.White
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp)
                            ) {
                                AsyncImage(
                                    model = activity.illustration_url,
                                    contentDescription = "Activity Illustration",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(48.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = activity.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (selectedMissionId == activity.id) Primary500 else Color.Black
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (selectedMissionId == activity.id) Primary500 else Secondary500,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = activity.category,
                                            color = Color.White,
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_star),
                                        contentDescription = "Star Icon",
//                                        tint = if (selectedMissionId == activity.id) Primary500 else Secondary500,
                                        tint = Secondary500,
                                        modifier = Modifier.size(22.dp)
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "+${activity.plus_xp} XP",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.createForum(caption, selectedMissionId)
                    onForumCreated()
                },
                enabled = !isLoading && caption.isNotEmpty() && selectedMissionId != null,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Kirim")
                }
            }
        }
    }
}
