package com.itsur.movil.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsur.movil.DataBase.Note
import com.itsur.movil.ViewModels.NoteViewModel
@Composable
fun NotesScreen(navController: NavController) {
    val viewModel: NoteViewModel = viewModel()
    val notes by viewModel.notes.observeAsState(initial = emptyList())
    var showDeleteIcon by remember { mutableStateOf(false) }
    var selectedNoteId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("edit_note")
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Nota")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (notes.isEmpty()) {
                    Text(
                        text = "No hay Notas Guardadas",
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(notes) { note ->
                            NoteItem(note = note, onClick = {
                                navController.navigate("edit_note/${note.id}")
                            }, onLongClick = {
                                selectedNoteId = note.id
                                showDeleteIcon = true
                            })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    if (showDeleteIcon && selectedNoteId != null) {
                        IconButton(
                            onClick = {
                                selectedNoteId?.let { viewModel.delete(it) }
                                showDeleteIcon = false
                                selectedNoteId = null
                            },
                            modifier = Modifier
                                .align(Alignment.BottomStart) // Mover el icono a la esquina inferior izquierda
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar Nota")
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(note: Note, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, fontSize = 20.sp, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.content, fontSize = 14.sp, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
