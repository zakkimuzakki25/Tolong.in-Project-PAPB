package com.papb.tolonginprojectpapb.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.papb.tolonginprojectpapb.MainActivity
import com.papb.tolonginprojectpapb.ui.components.headers.LogInHeader
import com.papb.tolonginprojectpapb.ui.screens.LogInScreen
import com.papb.tolonginprojectpapb.ui.theme.TolonginProjectPAPBTheme

class LogInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TolonginProjectPAPBTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        LogInHeader (
                            onBackClick = { finish() }
                        )
                    },
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        LogInScreen (
                            onLoginSuccess = {
                                val intent = Intent(this@LogInActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }
}