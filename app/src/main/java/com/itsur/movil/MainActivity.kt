package com.itsur.movil

import android.os.Build
import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.itsur.movil.navigation.NavScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itsur.movil.components.Menu_Lateral
import com.itsur.movil.components.NavegacionInferior
import com.itsur.movil.components.TopBar
import com.itsur.movil.navigation.BancNavigation
import com.itsur.movil.ui.theme.MovilTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovilTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                MainScreen()

                }

            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    Menu_Lateral(
        navController = navController,
        drawerState = drawerState
    ) {
        Contenido(navController = navController,
            drawerState = drawerState
            )
    }
    }



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contenido(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            // Mostrar el TopBar solo si no estamos en la pantalla de edición
            if (currentRoute != "edit_note" && !currentRoute?.startsWith("edit_note/").orFalse()) {
                TopBar(drawerState)
            }
        },
        bottomBar = {
            NavegacionInferior(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            BancNavigation(navController = navController)
        }
    }
}

@Composable
fun Boolean?.orFalse() = this ?: false

