package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.inputs.InputBar
import com.papb.tolonginprojectpapb.ui.components.inputs.InputType
import com.papb.tolonginprojectpapb.ui.theme.Neutral100
import com.papb.tolonginprojectpapb.ui.theme.SetTypography
import com.papb.tolonginprojectpapb.ui.theme.Success

@Composable
fun SignUpPasswordScreen(
    onNextClick: (String) -> Unit
) {
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var isTermsChecked by remember { mutableStateOf(false) }

    val hasMinLength = password.length >= 8
    val hasLowercase = password.any { it.isLowerCase() }
    val hasUppercase = password.any { it.isUpperCase() }
    val hasNumber = password.any { it.isDigit() }
    val hasSpecialChar = password.any { "!@#$%^&*()_+-=[]{}|;:'\",.<>?/".contains(it) }

    val isFormValid = password.isNotBlank() && passwordConfirm.isNotBlank() &&
            password == passwordConfirm && isTermsChecked &&
            hasMinLength && hasLowercase && hasUppercase && hasNumber && hasSpecialChar

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = "Password Akun", style = SetTypography.headlineSmall)

        InputBar(
            type = InputType.PASSWORD,
            value = password,
            onValueChange = { password = it },
            label = "Kata Sandi",
            placeHolder = "Kata Sandi"
        )

        Row {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                PasswordQualificationItem("Minimal 8 karakter", hasMinLength)
                PasswordQualificationItem("Karakter huruf kecil", hasLowercase)
                PasswordQualificationItem("Karakter huruf besar", hasUppercase)
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                PasswordQualificationItem("Karakter angka", hasNumber)
                PasswordQualificationItem("Karakter unik (!@#$)", hasSpecialChar)
            }
        }

        InputBar(
            type = InputType.PASSWORD,
            value = passwordConfirm,
            onValueChange = { passwordConfirm = it },
            label = "Konfirmasi Kata Sandi",
            placeHolder = "Konfirmasi Kata Sandi"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isTermsChecked,
                onCheckedChange = { isTermsChecked = it }
            )
            Text(
                text = "Saya telah memahami dan setuju dengan syarat dan ketentuan serta kebijakan privasi",
                style = SetTypography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        PrimerButton(
            text = "Daftar",
//            isActive = isFormValid,
            isActive = true,
            size = ButtonSize.LARGE,
            handle = { onNextClick(password) }
        )
    }
}

@Composable
fun PasswordQualificationItem(text: String, isValid: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val icon = if (isValid) Icons.Default.Check else Icons.Default.Close
        val color = if (isValid) Success else Neutral100

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color
        )
        Text(
            text = text,
            color = color,
            style = SetTypography.bodySmall
        )
    }
}

