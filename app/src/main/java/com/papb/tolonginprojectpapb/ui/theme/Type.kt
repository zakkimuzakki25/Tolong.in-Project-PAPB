package com.papb.tolonginprojectpapb.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.papb.tolonginprojectpapb.R

// Import font Maison Neue
val MaisonNeue = FontFamily(
    Font(R.font.maison_neue_light, FontWeight.Light),
    Font(R.font.maison_neue_book, FontWeight.Medium),
    Font(R.font.maison_neue_bold, FontWeight.SemiBold),
    Font(R.font.maison_neue_book, FontWeight.Bold)
)

val SetTypography = Typography(
    // Display Large (Heading 1)
    displayLarge = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    // Display Medium (Heading 2)
    displayMedium = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    // Display Small (Heading 3)
    displaySmall = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    // Headline Large (Heading 4)
    headlineLarge = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    // Headline Medium (Heading 1 - DemiBold)
    headlineMedium = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    // Headline Small (Heading 2 - DemiBold)
    headlineSmall = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    // Title Large (Heading 3 - DemiBold)
    titleLarge = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    // Title Medium (Heading 4 - DemiBold)
    titleMedium = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 18.6.sp,
        letterSpacing = 0.sp
    ),
    // Title Small (Body 1 - Medium)
    titleSmall = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    // Body Large (Body 1 - Medium)
    bodyLarge = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    // Body Medium (Body 2 - Medium)
    bodyMedium = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    ),
    // Body Small (Body 3 - Light)
    bodySmall = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    // Label Large
    labelLarge = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp
    ),
    // Label Medium
    labelMedium = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.sp
    ),
    // Label Small
    labelSmall = TextStyle(
        fontFamily = MaisonNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp
    )
)
