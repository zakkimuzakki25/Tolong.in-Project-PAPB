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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.popups.PopUpActionVolunteerSucces
import com.papb.tolonginprojectpapb.ui.components.popups.PopUpBergabungVolunteer
import com.papb.tolonginprojectpapb.ui.components.popups.PopUpIsiKegiatan
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.Primary200
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.Secondary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography
import com.papb.tolonginprojectpapb.viewmodel.VolunteerDetailViewModel

@Composable
fun VolunteerDetailScreen(viewModel: VolunteerDetailViewModel) {
    val volunteerDetail by viewModel.volunteerDetail.collectAsState()
    var showRequirementsDialog by remember { mutableStateOf(false) }
    var isJoined by remember { mutableStateOf(false) }
    var showJoinPopup by remember { mutableStateOf(false) }
    var showIsiKegiatanPopup by remember { mutableStateOf(false) }
    var showSuccessPopup by remember { mutableStateOf(false) }

    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(volunteerDetail) {
        if (currentUser != null && volunteerDetail != null) {
            viewModel.checkUserJoined(
                userId = currentUser.uid,
                volunteerId = volunteerDetail!!.id,
                onResult = { joined ->
                    isJoined = joined
                }
            )
        }
    }

    if (volunteerDetail == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val volunteer = volunteerDetail!!

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Row {
                Image(
                    painter = rememberAsyncImagePainter(volunteer.photo_url),
                    contentDescription = "Volunteer Photo",
                    modifier = Modifier
                        .size(width = 109.dp, height = 120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = volunteer.title,
                        style = SetTypography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Row {
                        Text(
                            text = "#Tolong.in",
                            color = Primary500,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                        Text(
                            text = " sesama",
                            color = Neutral200,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = volunteer.purpose,
                        fontSize = 10.sp,
                        color = Neutral500,
                        modifier = Modifier
                            .background(Secondary500, shape = RoundedCornerShape(50))
                            .padding(vertical = 1.dp, horizontal = 4.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_date_volunteer),
                            contentDescription = "Date Icon",
                            tint = Neutral200,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = volunteer.date,
                            style = SetTypography.bodyMedium,
                            color = Neutral200
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pin_volunteer),
                            contentDescription = "Location Icon",
                            tint = Neutral200,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = volunteer.location,
                            style = SetTypography.bodyMedium,
                            color = Neutral200
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Deskripsi",
                style = SetTypography.titleMedium,
                color = Color.Black
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = Primary200
            )
            Text(
                text = volunteer.description,
                color = Neutral200,
                modifier = Modifier.padding(top = 4.dp),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Rincian Tugas",
                style = SetTypography.titleMedium,
                color = Color.Black
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = Primary200
            )
            Text(
                text = volunteer.tasks.split("<>")
                    .mapIndexed { index, task -> "${index + 1}. $task" }
                    .joinToString("\n"),
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral200,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(thickness = 1.dp, color = Primary200)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showRequirementsDialog = true }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Lihat Persyaratan",
                    style = SetTypography.bodyLarge,
                    color = Primary500
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "Arrow Icon",
                    tint = Primary500
                )
            }
            HorizontalDivider(thickness = 1.dp, color = Primary200)

            Spacer(modifier = Modifier.weight(1f))

            if (isJoined) {
                PrimerButton(
                    text = "Isi Kegiatan",
                    isActive = true,
                    handle = { showIsiKegiatanPopup = true },
                    size = ButtonSize.LARGE
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            PrimerButton(
                text = if (isJoined) "Gabung" else "Tergabung",
                isActive = !isJoined,
                handle = { showJoinPopup = true },
                size = ButtonSize.LARGE
            )
        }

        if (showRequirementsDialog) {
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = { showRequirementsDialog = false },
                text = {
                    Column (
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Persyaratan",
                                style = SetTypography.titleMedium,
                                color = Color.Black
                            )
                            Column (
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_pin_volunteer),
                                        contentDescription = "Pin Icon",
                                        tint = Neutral200,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = volunteer.requirements.split("<>")[0],
                                        style = SetTypography.bodyMedium,
                                        color = Neutral200
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_profile_volunteer),
                                        contentDescription = "Pin Icon",
                                        tint = Neutral200,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = volunteer.requirements.split("<>")[1],
                                        style = SetTypography.bodyMedium,
                                        color = Neutral200
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_book_volunteer),
                                        contentDescription = "Pin Icon",
                                        tint = Neutral200,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = volunteer.requirements.split("<>")[2],
                                        style = SetTypography.bodyMedium,
                                        color = Neutral200
                                    )
                                }
                            }
                        }
                        Column (
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Pengalaman",
                                style = SetTypography.titleMedium,
                                color = Color.Black
                            )
                            Text(
                                text = "Berikut pengalaman yang dibutuhkan, tidak wajib, tetapi menjadi nilai tambah.\n",
                                style = SetTypography.bodyMedium,
                                color = Neutral200
                            )
                            Text(
                                text = volunteer.experiences.split("<>")
                                    .mapIndexed { index, task -> "${index + 1}. $task" }
                                    .joinToString("\n"),
                                style = SetTypography.bodyMedium,
                                color = Neutral200
                            )
                        }

                        Column (
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Keterampilan",
                                style = SetTypography.titleMedium,
                                color = Color.Black
                            )
                            Text(
                                text = volunteer.skills.split("<>")
                                    .mapIndexed { index, task -> "${index + 1}. $task" }
                                    .joinToString("\n"),
                                style = SetTypography.bodyMedium,
                                color = Neutral200
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showRequirementsDialog = false }) {
                        Text(text = "Tutup", color = Primary500)
                    }
                }
            )
        }

        if (showIsiKegiatanPopup) {
            PopUpIsiKegiatan(
                onDismiss = { showIsiKegiatanPopup = false },
                onConfirm = { kegiatan, dokumentasi ->
                    viewModel.submitActivity(
                        userId = currentUser?.uid ?: "",
                        volunteerId = volunteer.id,
                        kegiatan = kegiatan,
                        dokumentasi = dokumentasi,
                        onSuccess = {
                            currentUser?.uid?.let { userId ->
                                viewModel.addXPUser(
                                    userId = userId,
                                    onSuccess = {
                                        showIsiKegiatanPopup = false
                                        showSuccessPopup = true
                                    },
                                    onFailure = { error ->
                                        println("Gagal menambahkan XP: $error")
                                    }
                                )
                            }
                        },
                        onFailure = { error ->
                            println("Gagal menyimpan kegiatan: $error")
                        }
                    )
                }
            )
        }

        if (showJoinPopup) {
            PopUpBergabungVolunteer(
                onDismiss = { showJoinPopup = false },
                onConfirm = {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        val userId = user.uid
                        viewModel.joinVolunteer(
                            userId = userId,
                            volunteerId = volunteer.id,
                            onFailure = { error ->
                                println("Gagal bergabung: $error")
                            },
                            onSuccess = {
                                println("Berhasil bergabung dengan volunteer!")
                            }
                        )
                    } else {
                        println("Pengguna belum login!")
                    }
                    showJoinPopup = false
                }
            )
        }

        if (showSuccessPopup) {
            PopUpActionVolunteerSucces(
                onDismiss = { showSuccessPopup = false },
                title = volunteer.title,
                date = volunteer.date,
                location = volunteer.location,
                xp = 50
            )
        }
    }
}

