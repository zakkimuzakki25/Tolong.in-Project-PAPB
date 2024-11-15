package com.papb.tolonginprojectpapb.ui.theme

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

fun Modifier.buttonShadow(): Modifier = this
    .graphicsLayer {
        shadowElevation = 5.dp.toPx()
        shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
        clip = true
    }
    .background(Color(0x0D000000))

fun Modifier.navBarShadow(): Modifier = this
    .graphicsLayer {
        shadowElevation = 8.dp.toPx()
        translationY = -2.dp.toPx()
    }
    .background(Color(0x0F000000))

fun Modifier.elementShadow(): Modifier = this
    .graphicsLayer {
        shadowElevation = 8.dp.toPx()
        translationX = -2.dp.toPx()
        translationY = -2.dp.toPx()
    }
    .background(Color(0x14000000))

fun Modifier.flyingElementShadow(): Modifier = this
    .graphicsLayer {
        shadowElevation = 10.dp.toPx()
        translationY = 2.dp.toPx()
    }
    .background(Color(0x33000000))

fun Modifier.notificationShadow(): Modifier = this
    .graphicsLayer {
        shadowElevation = 20.dp.toPx()
        translationY = 2.dp.toPx()
    }
    .background(Color(0x33000000))

fun Modifier.headerShadow(): Modifier = this
    .graphicsLayer {
        shadowElevation = 8.dp.toPx()
        translationY = 2.dp.toPx()
    }
    .background(Color(0x08000000))