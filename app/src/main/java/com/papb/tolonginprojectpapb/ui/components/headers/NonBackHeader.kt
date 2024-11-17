package com.papb.tolonginprojectpapb.ui.components.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.theme.Primary500
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

@Composable
fun NonBackHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            )
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Primary500,
            style = SetTypography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}