package com.itsur.movil.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.itsur.movil.Models.Item_bottoms_nav.*
import com.itsur.movil.navigation.currentRoute

@Composable
fun NavegacionInferior(
    navController: NavHostController
){
    val menu_items= listOf(
        Item_bottoms_nav1,
        Item_bottoms_nav2,
        Item_bottoms_nav4
    )
    BottomAppBar {
        NavigationBar {
            menu_items.forEach{item ->
                val selected = currentRoute(navController) == item.ruta
                NavigationBarItem(
                    selected = selected,
                    onClick = {navController.navigate(item.ruta)},
                    icon = {
                        Icon(imageVector=item.icon,
                            contentDescription = item.title

                        )
                    },
                    label={
                       Text(text = item.title)
                    }
                )



            }
        }
    }
}