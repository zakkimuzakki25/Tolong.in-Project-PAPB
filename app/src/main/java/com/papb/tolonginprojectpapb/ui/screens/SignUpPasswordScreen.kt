package com.papb.tolonginprojectpapb.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignUpPasswordScreen(
    fullName: String,
    email: String,
    username: String,
    phone: String,
    birthDate: String
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Welcome, $fullName")
        Text(text = "Email: $email")
        Text(text = "Username: $username")
        Text(text = "Phone: $phone")
        Text(text = "Birth Date: $birthDate")
    }
}
