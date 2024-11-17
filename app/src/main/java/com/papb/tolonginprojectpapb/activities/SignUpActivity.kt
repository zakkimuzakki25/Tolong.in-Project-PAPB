package com.papb.tolonginprojectpapb.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.papb.tolonginprojectpapb.ui.components.headers.AuthHeader
import com.papb.tolonginprojectpapb.ui.screens.SignUpDataScreen
import com.papb.tolonginprojectpapb.ui.screens.SignUpPasswordScreen
import com.papb.tolonginprojectpapb.ui.theme.TolonginProjectPAPBTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TolonginProjectPAPBTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AuthHeader(
                            onBackClick = {}
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        SignUpScreenController()
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreenController() {
    var isPasswordScreen by remember { mutableStateOf(false) }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    if (!isPasswordScreen) {
        SignUpDataScreen(
            onNextClick = { name, mail, user, phoneNum, birth ->
                fullName = name
                email = mail
                username = user
                phone = phoneNum
                birthDate = birth
                isPasswordScreen = true
            }
        )
    } else {
        SignUpPasswordScreen(
            fullName = fullName,
            email = email,
            username = username,
            phone = phone,
            birthDate = birthDate
        )
    }
}
