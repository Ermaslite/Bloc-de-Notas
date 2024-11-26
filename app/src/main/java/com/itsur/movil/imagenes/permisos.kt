package com.itsur.movil.imagenes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import androidx.compose.runtime.DisposableEffect
import com.google.accompanist.permissions.isGranted
import androidx.compose.runtime.SideEffect
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsManager(content: @Composable () -> Unit) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val storagePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
        )
    )
    val audioPermissionState = rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

    LaunchedEffect(cameraPermissionState, storagePermissionsState, audioPermissionState) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
        if (!storagePermissionsState.allPermissionsGranted) {
            storagePermissionsState.launchMultiplePermissionRequest()
        }
        if (!audioPermissionState.status.isGranted) {
            audioPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status.isGranted && storagePermissionsState.allPermissionsGranted && audioPermissionState.status.isGranted) {
        content()
    }
}
