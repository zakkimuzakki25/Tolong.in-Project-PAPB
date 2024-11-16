package com.papb.tolonginprojectpapb.ui.components.buttons

import androidx.compose.ui.text.TextStyle
import com.papb.tolonginprojectpapb.ui.theme.SetTypography

enum class ButtonSize(val width: Int, val style: TextStyle) {
    LARGE(350, SetTypography.labelLarge),
    MEDIUM(171, SetTypography.labelMedium),
    SMALL(130, SetTypography.labelSmall)
}