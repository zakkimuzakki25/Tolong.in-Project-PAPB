package com.papb.tolonginprojectpapb.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.papb.tolonginprojectpapb.ui.components.headers.SignUpHeader
import com.papb.tolonginprojectpapb.ui.screens.SignUpDataScreen
import com.papb.tolonginprojectpapb.ui.screens.SignUpPasswordScreen
import com.papb.tolonginprojectpapb.ui.screens.SignUpSuccessScreen
import com.papb.tolonginprojectpapb.ui.screens.SignUpVerificationScreen
import com.papb.tolonginprojectpapb.ui.theme.TolonginProjectPAPBTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TolonginProjectPAPBTheme {
                val screensStack = remember { mutableStateListOf("SignUpDataScreen") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        SignUpHeader(
                            onBackClick = {
                                if (screensStack.size > 1) {
                                    screensStack.removeLastOrNull()
                                } else {
                                    finish()
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        SignUpScreenController(screensStack)
                    }
                }
            }
        }
    }
}

@Composable
fun SignUpScreenController(screensStack: MutableList<String>) {
    val currentScreen = screensStack.lastOrNull() ?: "SignUpDataScreen"

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var capturedUri by remember { mutableStateOf("") }

    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data?.getStringExtra("CAPTURED_URI")?.let { uri ->
                capturedUri = uri
            }
        }

    BackHandler(enabled = true) {
        if (screensStack.size > 1) {
            screensStack.removeLastOrNull()
        } else {
            (context as? ComponentActivity)?.finish()
        }
    }

    when (currentScreen) {
        "SignUpDataScreen" -> {
            SignUpDataScreen(
                onNextClick = { name, mail, user, phoneNum, birth ->
                    fullName = name
                    email = mail
                    username = user
                    phone = phoneNum
                    birthDate = birth
                    screensStack.add("SignUpPasswordScreen")
                }
            )
        }

        "SignUpPasswordScreen" -> {
            SignUpPasswordScreen(
                onNextClick = { pw ->
                    password = pw
                    screensStack.add("SignUpVerificationScreen")
                }
            )
        }

        "SignUpVerificationScreen" -> {
            SignUpVerificationScreen(
                onNextClick = {
                    screensStack.add("SignUpSuccessScreen")
                },
                onCancel = {
                    screensStack.removeLastOrNull()
                },
                onCaptureImage = {
                    val intent = Intent(context, CameraActivity::class.java)
                    launcher.launch(intent)
                },
                imageUri = capturedUri,
                fullName = fullName,
                email = email,
                username = username,
                phone = phone,
                birthDate = birthDate,
                password = password
            )
        }

        "SignUpSuccessScreen" -> {
            SignUpSuccessScreen()
        }
    }
}
