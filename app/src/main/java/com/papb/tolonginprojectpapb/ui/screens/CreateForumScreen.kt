package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.Secondary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography
import com.papb.tolonginprojectpapb.ui.viewmodel.CreateForumViewModel

@Composable
fun CreateForumScreen(viewModel: CreateForumViewModel, onForumCreated: () -> Unit) {
    val userInput by viewModel.userInput.collectAsState("Saya telah menyelesaikan aktivitas, sekarang giliranmu!")
    val isLoading by viewModel.isLoading.collectAsState()
    val recentActivities by viewModel.recentActivities.collectAsState()
    var selectedActivityId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            BackHeader("Unggah Kegiatan")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Ceritakan Kegiatanmu!",
                style = SetTypography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = userInput,
                onValueChange = viewModel::onUserInputChange,
                modifier = Modifier.fillMaxWidth().height(135.dp),
                placeholder = { Text("Ketik disini!", style = SetTypography.bodyMedium) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kegiatan Terakhir",
                style = SetTypography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            recentActivities.forEach { activity ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedActivityId = activity.id },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedActivityId == activity.id) Primary500 else Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = activity.imageUrl),
                            contentDescription = "Activity Image",
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = activity.title,
                                style = SetTypography.labelMedium,
                                color = if (selectedActivityId == activity.id) Color.White else Primary500
                            )
                            Text(
                                text = activity.category,
                                fontSize = 10.sp,
                                color = Neutral500,
                                modifier = Modifier
                                    .background(Secondary500, shape = RoundedCornerShape(50))
                                    .padding(vertical = 1.dp, horizontal = 4.dp)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_star),
                                contentDescription = "Star Icon",
                                tint = Secondary500,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "+${activity.xp} XP",
                                style = SetTypography.bodySmall,
                                color = if (selectedActivityId == activity.id) Color.White else Neutral500
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimerButton(
                text = if (isLoading) "Mengirim" else "Kirim",
                size = ButtonSize.LARGE,
                isActive = !isLoading && selectedActivityId != null && userInput.isNotEmpty(),
                handle = {
                    selectedActivityId?.let { activityId ->
                        viewModel.submitActivity(activityId)
                        onForumCreated()
                    }
                }
            )
        }
    }
}