package com.doublelift.snoozeloov2_nove24project.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.doublelift.snoozeloov2_nove24project.R

// Set of Material typography styles to start with
val Montserrat = FontFamily(
    Font(
        resId = R.font.montserrat_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.montserrat_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.montserrat_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.montserrat_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.montserrat_bold,
        weight = FontWeight.Bold
    ),
)

val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = Color.White,
    ),
    headlineLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight(500),
        fontSize = 24.sp,
        color = Color.Black,
        lineHeight = 29.26.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight(500),
        fontSize = 52.sp,
        color = SnoozeBlue,
        lineHeight = 64.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 17.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight(600),
        fontSize = 16.sp,
        lineHeight = 19.5.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight(500),
        fontSize = 42.sp,
        lineHeight = 51.2.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight(500),
        fontSize = 24.sp,
        lineHeight = 30.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight(600),
        fontSize = 82.sp,
        lineHeight = 100.sp,
    ),

)