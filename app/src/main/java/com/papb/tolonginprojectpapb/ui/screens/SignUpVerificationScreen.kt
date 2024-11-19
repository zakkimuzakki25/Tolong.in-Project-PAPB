package com.papb.tolonginprojectpapb.ui.screens

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
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral100
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun SignUpVerificationScreen(
    onNextClick: () -> Unit,
    onCancel: () -> Unit,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        if (imageUri.isEmpty()){
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
                if (imageUri == "") {
                    onCaptureImage()
                } else {
                    // Proses pendaftaran
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Ambil user ID dari Firebase Authentication
                                val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                                // Simpan data tambahan ke Firestore
                                val userData = hashMapOf(
                                    "fullName" to fullName,
                                    "username" to username,
                                    "phone" to phone,
                                    "birthDate" to birthDate,
                                    "email" to email
                                )

                                db.collection("users").document(userId)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        // Jika berhasil, lanjutkan ke layar success
                                        onNextClick()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            context,
                                            "Gagal menyimpan data: ${e.message}",
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
