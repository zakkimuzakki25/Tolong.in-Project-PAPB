package com.papb.tolonginprojectpapb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.headers.BackHeader
import com.papb.tolonginprojectpapb.ui.components.headers.NonBackHeader
import com.papb.tolonginprojectpapb.ui.theme.TolonginProjectPAPBTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(5000)
            keepSplashScreen = false
        }
        enableEdgeToEdge()
        setContent {
            TolonginProjectPAPBTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        BackHeader(
                            title = "Contoh Text",
                            onBackClick = {},
                        )

                        NonBackHeader(
                            title = "Contoh Text",
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        PrimerButton(
                            text = "Button 1",
                            isActive = true,
                            size = ButtonSize.LARGE,
                            handle = { /* Handle click */ }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        PrimerButton(
                            text = "Button 2",
                            isActive = true,
                            size = ButtonSize.SMALL,
                            handle = { /* Handle click */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TolonginProjectPAPBTheme {
        Greeting("Android")
    }
}