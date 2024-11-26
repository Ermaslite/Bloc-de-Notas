package com.itsur.movil.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.itsur.movil.screens.Acercade
import com.itsur.movil.screens.EditNoteScreen
import com.itsur.movil.screens.NewTaskScreen
import com.itsur.movil.screens.NotesScreen
import com.itsur.movil.screens.SettingsScreen
import com.itsur.movil.screens.TaskScreen
import com.itsur.movil.screens.imagesscreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BancNavigation(
    navController:NavHostController
){
    NavHost(navController= navController,startDestination= NavScreen.Notas.name)
    {

        composable("notas") {
            NotesScreen(navController)
        }
        composable(
            route = "edit_note/{noteId}",
            arguments = listOf(navArgument("noteId") { defaultValue = -1 })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            EditNoteScreen(navController, noteId)
        }
        composable("edit_note") {
            EditNoteScreen(navController, null)
        }
                composable("tareas") {
                    TaskScreen(navController)
                }
        composable(
            route = "newTask/{taskId}",
            arguments = listOf(navArgument("taskId") { defaultValue = -1 })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            NewTaskScreen(navController, taskId)
        }
        composable("newTask") {
            NewTaskScreen(navController, null)
        }

        composable(NavScreen.Configuracion.name) {
                    SettingsScreen()
                }
        composable(NavScreen.Acercade.name) {
                Acercade()
            }
        composable(NavScreen.imagesscreen.name) {
            imagesscreen()
        }
        }
    }

