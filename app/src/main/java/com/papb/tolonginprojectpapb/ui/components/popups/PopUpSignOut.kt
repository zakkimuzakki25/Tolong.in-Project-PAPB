package com.papb.tolonginprojectpapb.ui.components.popups

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.theme.Primary500

@Composable
fun PopUpSignOut(
    onDismiss: () -> Unit,
    onSignOut: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Keluar Akun",
                    style = MaterialTheme.typography.titleMedium.copy(color = Primary500),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Apakah Anda yakin ingin keluar dari akun Anda?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SecondaryButton(
                        text = "Tidak",
                        isActive = true,
                        size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                        handle = onDismiss
                    )

                    PrimerButton(
                        text = "Yakin",
                        isActive = true,
                        size = com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize.LARGE,
                        handle = onSignOut
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PopUpSignOutPreview() {
    MaterialTheme {
        PopUpSignOut(
            onDismiss = { /* Handle dismiss */ },
            onSignOut = { /* Handle sign out */ }
        )
    }
}
