package com.itsur.movil.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itsur.movil.R
import androidx.compose.material3.MaterialTheme as MaterialTheme1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Acercade() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Desarrollado Por:",
                    style = MaterialTheme1.typography.bodyLarge,
                    color = MaterialTheme1.colorScheme.primary
                )
                Text(
                    text = "Erik Rodriguez Rodriguez",
                    style = MaterialTheme1.typography.bodyMedium
                )
                Text(
                    text = "Agustin Paniagua Flores",
                    style = MaterialTheme1.typography.bodyMedium
                )
                Image(
                    painter = painterResource(id = R.drawable.marcapersonal), // Reemplaza con tu imagen
                    contentDescription = "",
                    modifier = Modifier.size(250.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Materia: Movil 1",
                    style = MaterialTheme1.typography.bodyLarge
                )
                Text(
                    text = "Maestro: Gustavo Ivan Vega",
                    style = MaterialTheme1.typography.bodyLarge
                )
                Image(
                    painter = painterResource(id = R.drawable.itsur), // Reemplaza con tu imagen
                    contentDescription = "",
                    modifier = Modifier.size(150.dp).clip(CircleShape)
                )
            }
        }


}

