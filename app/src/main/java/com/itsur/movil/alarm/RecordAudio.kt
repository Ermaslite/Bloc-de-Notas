package com.itsur.movil.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@Composable
fun RecordAudioDialog(
    onDismiss: () -> Unit,
    onAudioRecorded: (String) -> Unit
) {
    val context = LocalContext.current
    val audioRecorder = remember { AudioRecorder(context) }
    var isRecording by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf(0) }
    var timerJob by remember { mutableStateOf<Job?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            timerJob?.cancel()
            if (isRecording) {
                audioRecorder.stopRecording()
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Grabar Audio", style = MaterialTheme.typography.h6) },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(Color.Black).padding(16.dp)
            ) {
                Text("Tiempo de Grabación: ${timer}s", style = MaterialTheme.typography.body1, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isRecording) {
                        Button(onClick = {
                            audioRecorder.stopRecording()
                            timerJob?.cancel()
                            onAudioRecorded(audioRecorder.startRecording() ?: "")
                            isRecording = false
                        }) {
                            Text("Detener", style = MaterialTheme.typography.h6)
                        }
                    } else {
                        Button(onClick = {
                            audioRecorder.startRecording()?.let {
                                timer = 0
                                isRecording = true
                                timerJob?.cancel()
                                timerJob = CoroutineScope(Dispatchers.Main).launch {
                                    while (isRecording) {
                                        delay(1000L)
                                        timer += 1
                                    }
                                }
                            }
                        }) {
                            Text("Iniciar", style = MaterialTheme.typography.h6)
                        }
                    }
                    Button(onClick = onDismiss) {
                        Text("Cancelar", style = MaterialTheme.typography.h6)
                    }
                }
            }
        },
        buttons = {},
        backgroundColor = Color.Black // Añadir el color de fondo negro
    )
}
