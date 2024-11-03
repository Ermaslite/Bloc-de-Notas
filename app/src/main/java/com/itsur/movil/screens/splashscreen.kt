package com.itsur.movil.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.itsur.movil.MainActivity
import com.itsur.movil.R

@Composable
fun splashscreen(){
    val context= LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Image(
            painter = painterResource(id = R.drawable.libro),
            contentDescription = "Bienvenido a Tu Bloc de Notas"

        )
        Text(text = "Bienvenido",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onTertiaryContainer

        )

        OutlinedButton(onClick = {
            Log.d("SplashScreen","Continuar")
            val intent= Intent(
                context,
                MainActivity::class.java
            )
            context.startActivity(intent)
        }) {
            Text(text = "Continuar",
                    style = MaterialTheme.typography.titleMedium

            )

        }
    }
    }
@Composable
fun LoginScreen(){


}