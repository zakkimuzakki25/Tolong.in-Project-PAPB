package com.papb.tolonginprojectpapb.ui.components.popups

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.components.buttons.SecondaryButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Primary200
import com.papb.tolonginprojectpapb.ui.theme.Secondary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun PopUpActionVolunteerSucces(
    onDismiss: () -> Unit,
    title: String,
    date: String,
    location: String,
    xp: Int
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
                    text = "Kamu telah berhasil melakukan volunteer!",
                    style = SetTypography.labelLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = "Star Icon",
                        tint = Secondary500,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+$xp XP",
                        style = SetTypography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        },
        text = {
            Column {
                HorizontalDivider(thickness = 1.dp, color = Primary200)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    style = SetTypography.bodyMedium,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_date_volunteer),
                        contentDescription = "Date Icon",
                        tint = Neutral200,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = date,
                        style = SetTypography.bodyMedium,
                        color = Neutral200
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pin_volunteer),
                        contentDescription = "Location Icon",
                        tint = Neutral200,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = location,
                        style = SetTypography.bodyMedium,
                        color = Neutral200
                    )
                }
            }
        },
        confirmButton = {
            PrimerButton(
                size = ButtonSize.SMALL,
                text = "Selesai",
                handle = {onDismiss()},
                isActive = true
            )
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(12.dp)
    )
}
