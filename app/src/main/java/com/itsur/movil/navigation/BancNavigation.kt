package com.itsur.movil.navigation

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.itsur.movil.screens.Acercade
import com.itsur.movil.screens.EditNoteScreen
import com.itsur.movil.screens.FullScreenMediaScreen
import com.itsur.movil.screens.NewTaskScreen
import com.itsur.movil.screens.NotesScreen
import com.itsur.movil.screens.SettingsScreen
import com.itsur.movil.screens.TaskScreen
import com.itsur.movil.screens.imagesscreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BancNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavScreen.Notas.name) {
        composable(NavScreen.Notas.name) {
            NotesScreen(navController)
        }
        composable("edit_note/{noteId}", arguments = listOf(navArgument("noteId") { defaultValue = -1 })) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
            EditNoteScreen(navController, noteId)
        }
        composable("full_screen_media/{uri}/{mediaType}", arguments = listOf(
            navArgument("uri") { type = NavType.StringType },
            navArgument("mediaType") { type = NavType.StringType }
        )) { backStackEntry ->
            val uri = Uri.parse(backStackEntry.arguments?.getString("uri"))
            val mediaType = backStackEntry.arguments?.getString("mediaType") ?: "image"
            FullScreenMediaScreen(uri, mediaType, onBack = { navController.popBackStack() })
        }
        composable("edit_note") {
            EditNoteScreen(navController, null)
        }
        composable("tareas") {
            TaskScreen(navController)
        }
        composable("newTask/{taskId}", arguments = listOf(navArgument("taskId") { defaultValue = -1 })) { backStackEntry ->
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

