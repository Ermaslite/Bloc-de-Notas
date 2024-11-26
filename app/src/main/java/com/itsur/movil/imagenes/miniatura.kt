package com.itsur.movil.imagenes

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.itsur.movil.R
import android.media.ThumbnailUtils
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.asImageBitmap
import android.content.Context
import android.util.Log


@Composable
fun VideoThumbnail(videoUri: Uri, modifier: Modifier = Modifier, onPlayClick: () -> Unit) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(videoUri) {
        bitmap = getThumbnail(context, videoUri)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Video Thumbnail",
                modifier = Modifier.matchParentSize()
            )
        }
        IconButton(
            onClick = onPlayClick,
            modifier = Modifier.size(64.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_arrow),
                contentDescription = "Play",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

fun getThumbnail(context: Context, uri: Uri): Bitmap? {
    return try {
        ThumbnailUtils.createVideoThumbnail(
            context.contentResolver.openFileDescriptor(uri, "r")?.fileDescriptor.toString(),
            MediaStore.Images.Thumbnails.MINI_KIND
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
