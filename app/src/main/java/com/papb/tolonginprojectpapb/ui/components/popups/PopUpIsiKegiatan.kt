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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography
import java.util.UUID

@Composable
fun PopUpIsiKegiatan(
    onDismiss: () -> Unit,
    onConfirm: (String, Uri?) -> Unit
) {
    var kegiatan by remember { mutableStateOf("") }
    var dokumentasi by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val storageReference: StorageReference = FirebaseStorage.getInstance().reference.child("tolongin/activities")

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
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Pemantauan Habitat",
                    style = SetTypography.titleLarge,
                    color = Color.Black
                )
                Text(
                    text = "Kirim dokumentasimu saat melakukan pemantauan habitat!",
                    style = SetTypography.bodyMedium,
                    color = Neutral500
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Ceritakan Kegiatanmu!",
                        style = SetTypography.bodyLarge,
                        color = Color.Black
                    )
                    OutlinedTextField(
                        value = kegiatan,
                        onValueChange = { kegiatan = it },
                        placeholder = { Text(text = "Ketik disini!", style = SetTypography.bodyMedium,) },
                        textStyle = SetTypography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Dokumentasi",
                        style = SetTypography.bodyLarge,
                        color = Color.Black
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
            }
        },
        confirmButton = {
            PrimerButton(
                text = if (isLoading) "Mengirim" else "Kirim",
                isActive = kegiatan.isNotEmpty() && dokumentasi != null && !isLoading,
                handle = {
                    if (kegiatan.isNotEmpty() && dokumentasi != null) {
                        isLoading = true
                        val fileName = UUID.randomUUID().toString() + ".jpg"
                        val uploadRef = storageReference.child(fileName)

                        uploadRef.putFile(dokumentasi!!)
                            .addOnSuccessListener {
                                uploadRef.downloadUrl.addOnSuccessListener { uri ->
                                    onConfirm(kegiatan, uri)
                                    isLoading = false
                                    onDismiss()
                                }
                            }
                            .addOnFailureListener { e ->
                                println("Gagal mengunggah file: ${e.message}")
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
