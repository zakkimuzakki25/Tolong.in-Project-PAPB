package com.papb.tolonginprojectpapb.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.sp
import com.papb.tolonginprojectpapb.activities.LogInActivity
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.inputs.InputBar
import com.papb.tolonginprojectpapb.ui.components.inputs.InputType
import com.papb.tolonginprojectpapb.ui.theme.Based500
import com.papb.tolonginprojectpapb.ui.theme.Highlight
import com.papb.tolonginprojectpapb.ui.theme.Neutral100
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun SignUpDataScreen(
    onNextClick: (String, String, String, String, String) -> Unit
) {
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    val isFormValid = fullName.isNotBlank() && email.isNotBlank() && username.isNotBlank() && phone.isNotBlank() && birthDate.isNotBlank()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column {
            Text(text = "Sign Up", style = SetTypography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text= "Lengkapi data dirimu untuk daftar akun", style = SetTypography.bodyMedium, color = Based500)
        }

        InputBar(
            type = InputType.TEXT,
            value = fullName,
            onValueChange = { fullName = it },
            label = "Nama Lengkap",
            placeHolder = "Nama Lengkap"
        )
        InputBar(
            type = InputType.EMAIL,
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeHolder = "Email"
        )
        InputBar(
            type = InputType.TEXT,
            value = username,
            onValueChange = { username = it },
            label = "Username",
            placeHolder = "Username"
        )
        InputBar(
            type = InputType.PHONE,
            value = phone,
            onValueChange = { phone = it },
            label = "Nomor Telepon",
            placeHolder = "8..."
        )
        InputBar(
            type = InputType.DATE,
            value = birthDate,
            onValueChange = { birthDate = it },
            label = "Tanggal Lahir",
            placeHolder = "DD/MM/YYYY"
        )
//        Spacer(modifier = Modifier.height(8.dp))
        PrimerButton(
            text = "Lanjutkan",
//            isActive = isFormValid,
            isActive = true,
            size = ButtonSize.LARGE,
            handle = { onNextClick(fullName, email, username, phone, birthDate) }
        )

        Column(
            modifier = Modifier.padding(top = 0.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val annotatedText = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Neutral100)) {
                    append("Sudah memiliki akun? ")
                }
                pushStringAnnotation(tag = "LOGIN", annotation = "login")
                withStyle(
                    style = SpanStyle(
                        color = Highlight,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Masuk")
                }
                pop()
            }
            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                        .firstOrNull()?.let {
                            context.startActivity(Intent(context, LogInActivity::class.java))
                        }
                },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
