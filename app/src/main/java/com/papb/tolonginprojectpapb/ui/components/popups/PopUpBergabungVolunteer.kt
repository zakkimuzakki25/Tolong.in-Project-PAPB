package com.papb.tolonginprojectpapb.ui.components.popups

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun PopUpBergabungVolunteer(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.il_popup_volunteer),
                    contentDescription = "Ilustrasi Popup Volunteer",
                    modifier = Modifier
                        .size(width = 151.dp, height = 106.dp)
                )
                Text(
                    text = "Kamu ingin bergabung dalam Volunteer ini?",
                    style = SetTypography.labelLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Text(
                text = "Apakah Anda yakin ingin bergabung dengan kegiatan ini?",
                style = SetTypography.bodyMedium,
                color = Neutral200,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            PrimerButton(
                size = ButtonSize.SMALL,
                text = "Ya",
                handle = {onConfirm()},
                isActive = true
            )
        },
        dismissButton = {
            SecondaryButton(
                size = ButtonSize.SMALL,
                text = "Batal",
                handle = {onDismiss()},
                isActive = true
            )
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(12.dp)
    )
}
