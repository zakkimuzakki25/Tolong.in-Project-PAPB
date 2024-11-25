package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.viewmodel.ProfileEditViewModel

@Composable
fun ProfileEditScreen(
    onSaveClick: (String, String, String) -> Unit,
    onCancelClick: () -> Unit,
    viewModel: ProfileEditViewModel = viewModel()
) {
    val name by viewModel.name.observeAsState("")
    val phoneNumber by viewModel.phoneNumber.observeAsState("")
    val email by viewModel.email.observeAsState("")

    Scaffold(
        topBar = {
            BackHeader(
                title = "Edit Profil"
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
            ProfileAvatarSection()

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.updateProfile(it, phoneNumber, email) },
                label = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { viewModel.updateProfile(name, it, email) },
                label = { Text("Nomor Telepon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.updateProfile(name, phoneNumber, it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Batal
            SecondaryButton(
                text = "Batal",
                isActive = true,
                size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                handle = onCancelClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Simpan
            PrimerButton(
                text = "Simpan",
                isActive = true,
                size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                handle = { onSaveClick(name, phoneNumber, email) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
fun ProfileAvatarSection() {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        Icon(Icons.Default.Person, contentDescription = "Profile Avatar", modifier = Modifier.size(80.dp))
        IconButton(onClick = { /* Edit Avatar */ }) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Avatar", tint = MaterialTheme.colorScheme.primary)
        }
    }
}
