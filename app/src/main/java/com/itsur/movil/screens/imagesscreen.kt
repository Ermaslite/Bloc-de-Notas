package com.itsur.movil.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.itsur.movil.imagenes.ComposeFileProvider
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.itsur.movil.imagenes.PermissionsManager
import com.itsur.movil.imagenes.VideoPlayer
import com.itsur.movil.imagenes.VideoThumbnail
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun imagesscreen() {
    PermissionsManager {
        val context = LocalContext.current
        var mediaItems by remember { mutableStateOf(listOf<Uri>()) }
        var currentUri by remember { mutableStateOf<Uri?>(null) }
        var playingUri by remember { mutableStateOf<Uri?>(null) }

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    mediaItems = mediaItems + it
                }
            }
        )

        val cameraLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
            onResult = { success ->
                if (success && currentUri != null) {
                    mediaItems = mediaItems + currentUri!!
                }
            }
        )

        val videoLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CaptureVideo(),
            onResult = { success ->
                if (success && currentUri != null) {
                    mediaItems = mediaItems + currentUri!!
                }
            }
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Multimedia") },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { imagePickerLauncher.launch("image/*") },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Select Image")
                        }

                        Button(
                            onClick = {
                                currentUri = ComposeFileProvider.getMediaUri(context, ".jpg")
                                currentUri?.let { cameraLauncher.launch(it) }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Take Photo")
                        }

                        Button(
                            onClick = {
                                currentUri = ComposeFileProvider.getMediaUri(context, ".mp4")
                                currentUri?.let { videoLauncher.launch(it) }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Take Video")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 128.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(mediaItems) { uri ->
                            if (uri.toString().endsWith(".mp4")) {
                                if (playingUri == uri) {
                                    VideoPlayer(videoUri = uri, modifier = Modifier.height(200.dp))
                                } else {
                                    VideoThumbnail(
                                        videoUri = uri,
                                        modifier = Modifier.height(200.dp),
                                        onPlayClick = { playingUri = uri }
                                    )
                                }
                            } else {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .background(Color.Gray)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

