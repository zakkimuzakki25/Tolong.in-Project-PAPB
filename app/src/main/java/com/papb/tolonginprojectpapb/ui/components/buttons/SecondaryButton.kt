package com.papb.tolonginprojectpapb.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.papb.tolonginprojectpapb.ui.theme.Based100
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Primary500

@Composable
fun SecondaryButton(
    text: String,
    isActive: Boolean,
    size: ButtonSize,
    handle: () -> Unit
) {
    Button(
        onClick = handle,
        enabled = isActive,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) Color.White else Neutral200,
            contentColor = if (isActive) Primary500 else Based100
        ),
        border = if (isActive) BorderStroke(2.dp, Primary500) else BorderStroke(2.dp, Based100),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .size(height = 36.dp, width = size.width.dp)
    ) {
        Text(
            text = text,
            style = size.style
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonPreview() {
    Column {
        SecondaryButton(
            text = "Button 3",
            isActive = false,
            size = ButtonSize.SMALL,
            handle = { /* Handle click */ }
        )
    }
}
