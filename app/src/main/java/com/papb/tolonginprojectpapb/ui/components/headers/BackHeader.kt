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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papb.tolonginprojectpapb.R
import com.papb.tolonginprojectpapb.ui.theme.Primary500

@Composable
fun BackHeader(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier
                .size(32.dp)
                .clickable { onBackClick() }
                .background(Primary500, shape = MaterialTheme.shapes.extraLarge)
                .padding(8.dp)
                .align(Alignment.CenterStart)
                .offset(x = (-1).dp)
        )

        Text(
            text = title,
            color = Primary500,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackHeaderPreview() {
    BackHeader(
        title = "Sample Title",
        onBackClick = { /* Do nothing for preview */ }
    )
}
