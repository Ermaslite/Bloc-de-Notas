package com.itsur.movil.Models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddTask
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.itsur.movil.navigation.NavScreen

sealed class Item_bottoms_nav (
    val icon: ImageVector,
    val title: String,
    val ruta: String
){
    object Item_bottoms_nav1: Item_bottoms_nav(
        Icons.Outlined.AutoStories,
        "Notas",
        NavScreen.Notas.name
    )
    object Item_bottoms_nav2: Item_bottoms_nav(
        Icons.Outlined.AddTask,
        "Tareas",
        NavScreen.Tareas.name
    )
    object Item_bottoms_nav4: Item_bottoms_nav(
        Icons.Outlined.Settings,
        "Configuración",
        NavScreen.Configuracion.name
    )
}