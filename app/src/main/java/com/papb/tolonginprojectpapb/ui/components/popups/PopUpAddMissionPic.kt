package com.papb.tolonginprojectpapb.ui.components.popups

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography
import java.util.UUID

@Composable
fun PopUpAddMission(
    missionId: String,
    userId: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var dokumentasi by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var uploadError by remember { mutableStateOf<String?>(null) }

    val storageReference: StorageReference = FirebaseStorage.getInstance().reference.child("tolongin/dailymission")
    val firestore = FirebaseFirestore.getInstance()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        dokumentasi = uri
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Lakukan",
                    style = SetTypography.titleLarge,
                    color = Primary500,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Kirim dokumentasimu saat melakukan misi harian!",
                    style = SetTypography.bodyMedium,
                    color = Neutral500
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Dokumentasi",
                        style = SetTypography.labelMedium,
                        color = Color.Black,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color.White)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Neutral200, RoundedCornerShape(8.dp))
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (dokumentasi != null) {
                            Image(
                                painter = rememberAsyncImagePainter(dokumentasi),
                                contentDescription = "Preview Dokumentasi",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add),
                                contentDescription = "Tambah Dokumentasi",
                                tint = Neutral500,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
                if (uploadError != null) {
                    Text(
                        text = uploadError!!,
                        color = Color.Red,
                        style = SetTypography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            PrimerButton(
                text = if (isLoading) "Mengirim" else "Kirim",
                isActive = dokumentasi != null && !isLoading,
                handle = {
                    if (dokumentasi != null) {
                        isLoading = true
                        val fileName = "${UUID.randomUUID()}.jpg"
                        val uploadRef = storageReference.child(fileName)

                        uploadRef.putFile(dokumentasi!!)
                            .addOnSuccessListener {
                                uploadRef.downloadUrl.addOnSuccessListener { uri ->
                                    val userMissionData = hashMapOf(
                                        "user_id" to userId,
                                        "mission_id" to missionId,
                                        "documentation_url" to uri.toString(),
                                        "timestamp" to System.currentTimeMillis()
                                    )

                                    firestore.collection("users_missions")
                                        .add(userMissionData)
                                        .addOnSuccessListener {
                                            println("Mission successfully recorded!")
                                            isLoading = false
                                            onConfirm()
                                            onDismiss()
                                        }
                                        .addOnFailureListener { e ->
                                            uploadError = "Gagal mencatat misi: ${e.message}"
                                            isLoading = false
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                uploadError = "Gagal mengunggah dokumentasi: ${e.message}"
                                isLoading = false
                            }
                    }
                },
                size = ButtonSize.SMALL,
            )
        },
        dismissButton = {
            SecondaryButton(
                size = ButtonSize.SMALL,
                text = "Batal",
                isActive = true,
                handle = { onDismiss() }
            )
        }
    )
}

