package com.itsur.movil.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itsur.movil.R

private val lea = FontFamily(
    Font(R.font.light,
    FontWeight.Light),
    Font(R.font.regular,
        FontWeight.Normal
    )
)
// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = lea,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    ),
    titleMedium = TextStyle(
        fontFamily = lea,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = lea,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    )
 )