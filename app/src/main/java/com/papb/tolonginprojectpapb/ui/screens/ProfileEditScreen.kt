package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.ui.components.inputs.InputBar
import com.papb.tolonginprojectpapb.ui.components.inputs.InputType
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.viewmodel.ProfileViewModel

@Composable
fun ProfileEditScreen(
    onSaveClick: (String, String, String) -> Unit,
    onCancelClick: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val name by viewModel.name.observeAsState("")
    val phoneNumber by viewModel.phoneNumber.observeAsState("")
    val birthDate by viewModel.birthDate.observeAsState("")

    Scaffold(
        topBar = {
            BackHeader(
                title = "Edit Profil"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gambar profil
            AsyncImage(
                model = viewModel.avatarUrl.observeAsState("").value,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(72.dp)
                    .background(Primary500, RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputBar(
                type = InputType.TEXT,
                value = name,
                onValueChange = { viewModel.updateName(it) },
                label = "Nama Lengkap",
                placeHolder = "Masukkan nama lengkap"
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputBar(
                type = InputType.PHONE,
                value = phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = "Nomor Telepon",
                placeHolder = "Masukkan nomor telepon"
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputBar(
                type = InputType.DATE,
                value = birthDate,
                onValueChange = { viewModel.updateBirthDate(it) },
                label = "Tanggal Lahir",
                placeHolder = "Masukkan tanggal lahir"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol batal
            SecondaryButton(
                text = "Batal",
                isActive = true,
                size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                handle = onCancelClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            PrimerButton(
                text = "Simpan",
                isActive = true,
                size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                handle = { onSaveClick(name, phoneNumber, birthDate) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }
    }
}

