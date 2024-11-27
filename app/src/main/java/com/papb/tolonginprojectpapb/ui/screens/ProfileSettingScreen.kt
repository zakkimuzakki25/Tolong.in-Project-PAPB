package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.ui.components.popups.PopUpSignOut

@Composable
fun ProfileSettingScreen(
    onEditProfileClick: () -> Unit,
    onConfirmSignOut: () -> Unit
) {
    var showSignOutPopup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BackHeader(
                title = "Pengaturan"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Layanan Pengaduan
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Layanan Pengaduan", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Email", style = MaterialTheme.typography.bodySmall)
                    Text("tolongin@gmail.com", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Telepon", style = MaterialTheme.typography.bodySmall)
                    Text("(021) 87392045", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            SecondaryButton(
                text = "Edit Profil",
                isActive = true,
                size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                handle = onEditProfileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrimerButton(
                text = "Keluar",
                isActive = true,
                size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                handle = { showSignOutPopup = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Mendorong tombol lebih dekat ke tengah layar
        }
    }

    if (showSignOutPopup) {
        PopUpSignOut(
            onDismiss = { showSignOutPopup = false },
            onSignOut = {
                showSignOutPopup = false
                onConfirmSignOut()
            }
        )
    }
}