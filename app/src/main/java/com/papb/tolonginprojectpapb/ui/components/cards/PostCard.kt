package com.papb.tolonginprojectpapb.ui.components.cards

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.entities.CampaignMission
import com.papb.tolonginprojectpapb.entities.Forum
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.format.DateTimeFormatterBuilder

@RequiresApi(Build.VERSION_CODES.O)
fun parseCustomTimestamp(timestamp: String): Instant? {
    return try {
        val formatter: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("EEE MMM dd HH:mm:ss 'GMT'XXX yyyy")
            .toFormatter(Locale.ENGLISH)

        val zonedDateTime = formatter.parse(timestamp, java.time.ZonedDateTime::from)
        zonedDateTime.toInstant()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTimeAgo(timestamp: String): String {
    return try {
        val postTime = parseCustomTimestamp(timestamp) ?: return "Waktu tidak valid"
        val currentTime = Instant.now()
        val duration = Duration.between(postTime, currentTime)

        when {
            duration.toMinutes() < 1 -> "Baru saja"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} menit yang lalu"
            duration.toHours() < 24 -> "${duration.toHours()} jam yang lalu"
            duration.toDays() < 7 -> "${duration.toDays()} hari yang lalu"
            else -> DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault()).format(postTime)
        }
    } catch (e: Exception) {
        "Waktu tidak valid"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostCard(forum: Forum, mission: CampaignMission) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = forum.avatar_url,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(40.dp)
                        .background(Primary500, RoundedCornerShape(50)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = forum.username,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = formatTimeAgo(forum.time),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = forum.caption,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                color = Color.DarkGray
            )

            BigMissionCard(
                title = mission.title,
                illustrationUrl = mission.illustration_url,
                id = mission.id,
                plusXp = mission.plus_xp,
                description = mission.description,
                category = mission.category
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Likes",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "${forum.likes} Likes",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_comment),
                        contentDescription = "Comments",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "${forum.comments} Comments",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}