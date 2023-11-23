package com.github.h3r3t1c.shoppingCartController.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Red400 = Color(0xFFCF6679)

val Blue500 = Color(0xFF2196F3)
val Blue700 = Color(0xFF1976D2)
val Yellow200 = Color(0xffFFE082)
val Green500 = Color(0xff4CAF50)

internal val wearColorPalette: Colors = Colors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Yellow200,
    secondaryVariant = Yellow200,
    error = Red400,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black
)