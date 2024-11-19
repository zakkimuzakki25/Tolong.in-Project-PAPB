package com.papb.tolonginprojectpapb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.papb.tolonginprojectpapb.activities.SignUpActivity
import com.papb.tolonginprojectpapb.ui.theme.TolonginProjectPAPBTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
//        val splashscreen = installSplashScreen()
//        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
//        splashscreen.setKeepOnScreenCondition { keepSplashScreen }

        firebaseAuth = FirebaseAuth.getInstance()

        lifecycleScope.launch {
//            delay(1000)
//            keepSplashScreen = false

            if (firebaseAuth.currentUser == null) {
                val intent = Intent(this@MainActivity, SignUpActivity::class.java)
                startActivity(intent)
//                finish() // Close MainActivity to prevent going back
            } else {
                setContent {
                    TolonginProjectPAPBTheme {
                        Text("Hello")
                    }
                }
            }
        }

        enableEdgeToEdge()
    }
}
