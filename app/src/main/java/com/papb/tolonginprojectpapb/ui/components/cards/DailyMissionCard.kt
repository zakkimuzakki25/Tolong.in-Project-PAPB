package com.papb.tolonginprojectpapb.ui.components.cards

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.activities.DailyMissionDetailActivity
import com.papb.tolonginprojectpapb.ui.components.buttons.ButtonSize
import com.papb.tolonginprojectpapb.ui.components.buttons.PrimerButton
import com.papb.tolonginprojectpapb.ui.theme.Neutral500
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.Secondary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun DailyMissionCard(
    id: String,
    title: String,
    illustrationUrl: String,
    plusXp: Int,
    category: String
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.TopStart)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        style = SetTypography.labelMedium,
                        color = Primary500
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = category,
                        color = Neutral500,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .background(
                                color = Secondary500,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Star Icon",
                            tint = Secondary500,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "+$plusXp XP",
                            style = SetTypography.bodySmall,
                            color = Color.Black
                        )
                    }
                }
                AsyncImage(
                    model = illustrationUrl,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Tombol di bagian bawah
        PrimerButton(
            text = "Lakukan",
            isActive = true,
            handle = {
                val intent = Intent(context, DailyMissionDetailActivity::class.java).apply {
                    putExtra("MISSION_ID", id)
                }
                context.startActivity(intent)
            },
            size = ButtonSize.SMALL,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}
