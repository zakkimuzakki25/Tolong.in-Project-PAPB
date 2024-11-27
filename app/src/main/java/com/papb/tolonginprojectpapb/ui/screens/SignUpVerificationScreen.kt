package com.papb.tolonginprojectpapb.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral100
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun SignUpVerificationScreen(
    onNextClick: () -> Unit,
    onCaptureImage: () -> Unit,
    imageUri: String,
    fullName: String,
    email: String,
    username: String,
    phone: String,
    birthDate: String,
    password: String
) {
    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val avatarUrls = listOf(
        "https://firebasestorage.googleapis.com/v0/b/flonn-pubek.appspot.com/o/tolongin%2Favatar%2Favatar1.png?alt=media&token=d71f2b83-7d4d-4b94-8875-fd030a5f23b4",
        "https://firebasestorage.googleapis.com/v0/b/flonn-pubek.appspot.com/o/tolongin%2Favatar%2Favatar2.png?alt=media&token=fbf3bf6c-8258-42f8-8975-50f4138ffcd8",
        "https://firebasestorage.googleapis.com/v0/b/flonn-pubek.appspot.com/o/tolongin%2Favatar%2Favatar3.png?alt=media&token=58881db4-9059-46c0-93ec-c50152fefaad"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        if (imageUri.isEmpty()) {
            Text(
                text = "Verifikasi Identitasmu",
                style = SetTypography.headlineSmall,
            )

            Text(
                text = "Kami diharuskan memverifikasi identitas Anda sebelum Anda dapat menggunakan aplikasi. Informasi Anda akan dienkripsi dan disimpan dengan aman.",
                style = SetTypography.bodySmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.ex_ktp),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            Text(
                text = "Foto KTP Anda",
                style = SetTypography.headlineMedium,
            )

            Text(
                text = "Pastikan bahwa KTP Anda sudah sesuai",
                style = SetTypography.bodySmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Neutral100)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimerButton(
            text = "Lanjutkan",
            isActive = true,
            size = ButtonSize.LARGE,
            handle = {
                if (imageUri.isEmpty()) {
                    onCaptureImage()
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                                val storageReference = FirebaseStorage.getInstance()
                                    .reference
                                    .child("tolongin/ktp/${userId}.jpg")

                                val uploadTask = storageReference.putFile(Uri.parse(imageUri))

                                uploadTask.addOnSuccessListener {
                                    storageReference.downloadUrl.addOnSuccessListener { uri ->

                                        val randomAvatarUrl = avatarUrls.random()

                                        val userData = hashMapOf(
                                            "fullname" to fullName,
                                            "username" to username,
                                            "phone_number" to phone,
                                            "birth_date" to birthDate,
                                            "email" to email,
                                            "ktp_url" to uri.toString(),
                                            "avatar_url" to randomAvatarUrl
                                        )

                                        db.collection("users").document(userId)
                                            .set(userData)
                                            .addOnSuccessListener {
                                                onNextClick()
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(
                                                    context,
                                                    "Gagal menyimpan data: ${e.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                }.addOnFailureListener { e ->
                                    Toast.makeText(
                                        context,
                                        "Gagal mengunggah KTP: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                val errorMessage = task.exception?.message ?: "Pendaftaran gagal"
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

