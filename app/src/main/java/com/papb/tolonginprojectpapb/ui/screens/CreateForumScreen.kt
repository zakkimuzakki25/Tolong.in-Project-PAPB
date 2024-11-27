package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.viewmodel.CreateForumViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateForumScreen(viewModel: CreateForumViewModel, onForumCreated: () -> Unit) {
    val userInput by viewModel.userInput.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Unggah Kegiatan") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 8.dp)
                )
                Column {
                    Text(text = "Naufal Afkaar", fontWeight = FontWeight.Bold)
                    Text(text = "@afkaaroar", style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ceritakan Kegiatanmu!",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = userInput,
                onValueChange = viewModel::onUserInputChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ketik disini!") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kegiatan Terakhir",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            RecentActivityCard(
                title = "Hemat Energi",
                category = "Lingkungan",
                xp = "+20",
                iconResId = R.drawable.ic_profile2
            )
            Spacer(modifier = Modifier.height(8.dp))
            RecentActivityCard(
                title = "Jalan Kaki",
                category = "Travel",
                xp = "+50",
                iconResId = R.drawable.ic_add_forum
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.submitActivity(); onForumCreated() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Kirim")
                }
            }
        }
    }
}

@Composable
fun RecentActivityCard(title: String, category: String, xp: String, iconResId: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Title and Category
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = category,
                    color = Color(0xFFFFA000),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = xp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
            }
        }
    }
}
