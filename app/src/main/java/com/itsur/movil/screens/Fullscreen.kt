package com.itsur.movil.screens

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsur.movil.alarm.AudioPlayer
import com.itsur.movil.imagenes.VideoPlayer
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FullScreenMediaScreen(uri: Uri, mediaType: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ver multimedia",fontSize = 14.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (mediaType) {
                "image" -> {
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                "video" -> {
                    VideoPlayer(videoUri = uri, modifier = Modifier.fillMaxSize())
                }
                "audio" -> {
                    AudioPlayer(audioUri = uri, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
