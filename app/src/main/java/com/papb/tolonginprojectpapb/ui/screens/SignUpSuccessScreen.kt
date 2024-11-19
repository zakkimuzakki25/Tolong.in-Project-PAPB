package com.papb.tolonginprojectpapb.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.activities.CameraActivity
import com.papb.tolonginprojectpapb.activities.LogInActivity
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.theme.Based500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun SignUpSuccessScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(22.dp),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Pendaftaran Berhasil!", style = SetTypography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Akunmu telah berhasil dibuat! Masuk ke akunmu sekarang untuk mengakses berbagai konten menarik Tolong.In",
                style = SetTypography.bodyMedium,
                color = Based500,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            PrimerButton(
                text = "Masuk",
                isActive = true,
                size = ButtonSize.LARGE,
                handle = {
                    val intent = Intent(context, LogInActivity::class.java)
                    context.startActivity(intent)
                    (context as? android.app.Activity)?.finish()
                }
            )
        }

    }
}
