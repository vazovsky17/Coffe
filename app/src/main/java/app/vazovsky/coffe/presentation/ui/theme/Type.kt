package app.vazovsky.coffe.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.vazovsky.coffe.R

val UiDisplayFontFamily = FontFamily(
    Font(R.font.sfuidisplay_thin, FontWeight.Thin),
    Font(R.font.sfuidisplay_light, FontWeight.Light),
    Font(R.font.sfuidisplay_regular,FontWeight.Normal),
    Font(R.font.sfuidisplay_bold, FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    labelLarge = TextStyle(
        fontFamily = UiDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = UiDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = UiDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = UiDisplayFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = UiDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = UiDisplayFontFamily,
        fontWeight = FontWeight.Thin,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
    ),
)