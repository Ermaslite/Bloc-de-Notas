package com.itsur.movil.screens

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itsur.movil.DataBase.Note
import com.itsur.movil.ViewModels.NoteViewModel
import com.itsur.movil.alarm.AlarmItem
import com.itsur.movil.alarm.AlarmSchedulerImpl
import com.itsur.movil.alarm.AudioPlayer
import com.itsur.movil.alarm.RecordAudioDialog
import com.itsur.movil.imagenes.ComposeFileProvider
import com.itsur.movil.imagenes.VideoThumbnail
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(navController: NavController, noteId: Int?) {
    val viewModel: NoteViewModel = viewModel()
    val note by viewModel.note.observeAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var mediaUris by remember { mutableStateOf<List<String>>(emptyList()) }
    var mediaItems by remember { mutableStateOf(listOf<Uri>()) }
    var reminders by remember { mutableStateOf<List<AlarmItem>>(emptyList()) }
    var reminderCount by remember { mutableStateOf(1) }

    val context = LocalContext.current
    val alarmScheduler = AlarmSchedulerImpl(context)
    var currentUri by remember { mutableStateOf<Uri?>(null) }
    var playingUri by remember { mutableStateOf<Uri?>(null) }
    var showAudioDialog by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                mediaItems = mediaItems + it
                mediaUris = mediaUris + it.toString()
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && currentUri != null) {
                mediaItems = mediaItems + currentUri!!
                mediaUris = mediaUris + currentUri.toString()
            }
        }
    )

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if (success && currentUri != null) {
                mediaItems = mediaItems + currentUri!!
                mediaUris = mediaUris + currentUri.toString()
            }
        }
    )

    LaunchedEffect(noteId) {
        noteId?.let {
            viewModel.getNoteById(it)
        }
    }

    LaunchedEffect(note) {
        note?.let {
            title = it.title
            content = it.content
            mediaUris = it.mediaUris
            mediaItems = mediaUris.map { uri -> Uri.parse(uri) }
            reminders = it.reminders.map { alarmItem ->
                AlarmItem(alarmItem.alarmId, alarmItem.alarmTime ?: LocalDateTime.now(), alarmItem.message)
            }
            reminderCount = reminders.size + 1
        }
    }

    if (showAudioDialog) {
        RecordAudioDialog(
            onDismiss = { showAudioDialog = false },
            onAudioRecorded = { audioPath ->
                showAudioDialog = false
                if (audioPath.isNotEmpty()) {
                    mediaUris = mediaUris + audioPath
                    mediaItems = mediaItems + Uri.parse(audioPath)
                }
            }
        )
    }

    if (showDatePickerDialog) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, selectedDateTime.hour, selectedDateTime.minute)
                showTimePickerDialog = true
                showDatePickerDialog = false
            },
            selectedDateTime.year,
            selectedDateTime.monthValue - 1,
            selectedDateTime.dayOfMonth
        ).show()
    }

    if (showTimePickerDialog) {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                selectedDateTime = selectedDateTime.withHour(hourOfDay).withMinute(minute)
                val alarmMessage = "Recordatorio $reminderCount: $title"
                val alarmItem = AlarmItem(reminderCount, selectedDateTime, alarmMessage)
                reminders = reminders + alarmItem
                alarmScheduler.schedule(alarmItem)
                reminderCount++
                showTimePickerDialog = false
            },
            selectedDateTime.hour,
            selectedDateTime.minute,
            true
        ).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Nota", fontSize = 14.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.addOrUpdate(Note(
                            id = noteId ?: 0,
                            title = title,
                            content = content,
                            mediaUris = mediaUris,
                            reminders = reminders
                        ))
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "Guardar Nota")
                    }
                }
            )
        },
        floatingActionButton = {
            var fabExpanded by remember { mutableStateOf(false) }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                FloatingActionButton(onClick = { fabExpanded = !fabExpanded }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Multimedia")
                }

                if (fabExpanded) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(end = 16.dp, bottom = 56.dp)
                    ) {
                        FloatingActionButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                            Icon(Icons.Default.Image, contentDescription = "Agregar Imagen")
                        }
                        FloatingActionButton(onClick = {
                            currentUri = ComposeFileProvider.getMediaUri(context, ".jpg")
                            currentUri?.let { cameraLauncher.launch(it) }
                        }) {
                            Icon(Icons.Default.CameraAlt, contentDescription = "Tomar Foto")
                        }
                        FloatingActionButton(onClick = {
                            currentUri = ComposeFileProvider.getMediaUri(context, ".mp4")
                            currentUri?.let { videoLauncher.launch(it) }
                        }) {
                            Icon(Icons.Default.Videocam, contentDescription = "Grabar Video")
                        }
                        FloatingActionButton(onClick = {
                            showAudioDialog = true
                        }) {
                            Icon(Icons.Default.Mic, contentDescription = "Grabar Audio")
                        }
                        FloatingActionButton(onClick = {
                            showDatePickerDialog = true
                        }) {
                            Icon(Icons.Default.Alarm, contentDescription = "Programar Alarma")
                        }
                    }
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Título") },
                        textStyle = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Descripción") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 56.dp,)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Mostrar multimedia actual
                    mediaItems.forEach { uri ->
                        if (uri.toString().endsWith(".mp4")) {
                            VideoThumbnail(
                                videoUri = uri,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                onPlayClick = { playingUri = uri }
                            )
                        } else if (uri.toString().endsWith(".mp3")) {
                            AudioPlayer(audioUri = uri, modifier = Modifier.height(56.dp))
                        } else {
                            AsyncImage(
                                model = uri,
                                contentDescription = "Multimedia",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    // Mostrar los recordatorios
                    reminders.forEach { reminder ->
                        Text(text = "Recordatorio: ${reminder.message} a las ${reminder.getFormattedTime()}")
                    }
                }
            }
        }
    )
}
