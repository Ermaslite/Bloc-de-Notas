package com.itsur.movil.Models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import com.itsur.movil.navigation.NavScreen

sealed class items_menu_lateral(
    val icono: ImageVector,
    val title: String,
    val ruta: String
){

    object items_menu_lateral2: com.itsur.movil.Models.items_menu_lateral(
        Icons.Outlined.Info,
        "Acerca de",
        NavScreen.Acercade.name
    )
    object items_menu_lateral1: com.itsur.movil.Models.items_menu_lateral(
        Icons.Outlined.AddAPhoto,
        "Imagenes",
        NavScreen.imagesscreen.name
    )
}