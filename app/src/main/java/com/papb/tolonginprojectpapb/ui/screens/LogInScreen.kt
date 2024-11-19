package com.papb.tolonginprojectpapb.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.papb.tolonginprojectpapb.activities.SignUpActivity
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.inputs.InputBar
import com.papb.tolonginprojectpapb.ui.components.inputs.InputType
import com.papb.tolonginprojectpapb.ui.theme.Based500
import com.papb.tolonginprojectpapb.ui.theme.Highlight
import com.papb.tolonginprojectpapb.ui.theme.Neutral100
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun LogInScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val isFormValid = username.isNotBlank() && password.isNotBlank()

    BackHandler(enabled = true) {
        (context as? ComponentActivity)?.finish()
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column {
            Text(text = "Masuk", style = SetTypography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text= "Masuk ke akun untuk menggunakan aplikasi", style = SetTypography.bodyMedium, color = Based500)
        }

        InputBar(
            type = InputType.TEXT,
            value = username,
            onValueChange = { username = it },
            label = "Username",
            placeHolder = "Username"
        )
        InputBar(
            type = InputType.PASSWORD,
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeHolder = "Password"
        )

        PrimerButton(
            text = "Masuk",
            isActive = isFormValid && !isLoading, // Tombol aktif jika form valid dan tidak loading
            size = ButtonSize.LARGE,
            handle = {
                isLoading = true
                val auth = FirebaseAuth.getInstance()

                // Login ke Firebase
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener { task ->
                        isLoading = false
                        if (task.isSuccessful) {
                            // Login berhasil, lanjutkan ke layar berikutnya
                            onLoginSuccess()
                        } else {
                            // Login gagal, tampilkan pesan kesalahan
                            val errorMessage =
                                task.exception?.message ?: "Login gagal, periksa kembali kredensial Anda"
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        )

        Column(
            modifier = Modifier.padding(top = 0.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Neutral100)) {
                    append("Belum memiliki akun? ")
                }
                pushStringAnnotation(tag = "SIGNUP", annotation = "Signup")
                withStyle(
                    style = SpanStyle(
                        color = Highlight,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Daftar")
                }
                pop()
            }
            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "SIGNUP", start = offset, end = offset)
                        .firstOrNull()?.let {
                            context.startActivity(Intent(context, SignUpActivity::class.java))
                        }
                },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
