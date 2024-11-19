package com.papb.tolonginprojectpapb.ui.components.headers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.theme.Neutral400

@Composable
fun LogInHeader(
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.ic_wave_background2),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 0.dp),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp, bottom = 0.dp, start = 22.dp, end = 22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            width = 0.5.dp,
                            color = Neutral400,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .background(Color.White, shape = RoundedCornerShape(5.dp))
                        .padding(10.dp)
                ) {
                    IconButton(
                        onClick = { (onBackClick()) },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo_png),
                contentDescription = "Logo",
                modifier = Modifier.size(width = 135.dp, height = 36.47.dp)
            )
        }
    }
}