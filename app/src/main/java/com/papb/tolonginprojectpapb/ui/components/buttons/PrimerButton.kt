package com.papb.tolonginprojectpapb.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.papb.tolonginprojectpapb.ui.theme.Based100
import com.papb.tolonginprojectpapb.ui.theme.Neutral200
import com.papb.tolonginprojectpapb.ui.theme.Primary500

@Composable
fun PrimerButton(
    text: String,
    isActive: Boolean,
    size: ButtonSize,
    handle: () -> Unit
) {
    Button(
        onClick = handle,
        enabled = isActive,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) Primary500 else Neutral200,
            contentColor = if (isActive) Based100 else Based100
        ),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .size(height = 35.dp, width = size.width.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimerButtonPreview() {
    Column {
        PrimerButton(
            text = "Button 1",
            isActive = true,
            size = ButtonSize.LARGE,
            handle = { /* Handle click */ }
        )
        Spacer(modifier = Modifier.height(8.dp))

        PrimerButton(
            text = "Button 2",
            isActive = false,
            size = ButtonSize.MEDIUM,
            handle = { /* Handle click */ }
        )
    }
}
