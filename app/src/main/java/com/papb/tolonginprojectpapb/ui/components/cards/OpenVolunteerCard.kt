package com.papb.tolonginprojectpapb.ui.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.papb.tolonginprojectpapb.entities.OpenVolunteer
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.Secondary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun OpenVolunteerCard(volunteer: OpenVolunteer) {
    Card(
        modifier = Modifier
            .padding(0.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
            val painter = rememberAsyncImagePainter(volunteer.photo_url)
            Image(
                painter = painter,
                contentDescription = "Volunteer Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Blue.copy(alpha = 0.3f))
                    .padding(10.dp)
                    .blur(28.dp)
            ) {
                Text(
                    text = volunteer.date,
                    fontSize = 10.sp,
                    color = Neutral500,
                    modifier = Modifier
                        .background(Secondary500, shape = RoundedCornerShape(50))
                        .padding(vertical = 1.dp, horizontal = 4.dp)
                )

                Text(
                    text = volunteer.title,
                    style = SetTypography.labelLarge,
                    modifier = Modifier.padding(top = 6.dp),
                    color = Color.White
                )

                Text(
                    text = volunteer.highlight,
                    fontSize = 8.sp,
                    modifier = Modifier.padding(top = 2.dp),
                    color = Color.White
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
                ) {
                    PrimerButton(
                        text = "Beraksi",
                        size = ButtonSize.SMALL,
                        isActive = true,
                        handle = {}
                    )
                }
            }
        }
    }
}
