package com.itsur.movil.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsur.movil.DataBase.Task
import com.itsur.movil.ViewModels.TaskViewModel
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(navController: NavController) {
    val viewModel: TaskViewModel = viewModel()
    val tasks by viewModel.allTasks.observeAsState(listOf())
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("newTask") }) {
                Icon(Icons.Default.Add, contentDescription = "Nueva Tarea")
            }
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(tasks) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(Color.Black)
                            .padding(16.dp)
                            .combinedClickable(
                                onClick = { },
                                onLongClick = { selectedTask = task }
                            )
                    ) {
                        Column {
                            Text(
                                text = task.description,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1, // Limita el texto a una línea
                                overflow = TextOverflow.Ellipsis // Agrega "..." si el texto es muy largo
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${task.date} a las ${task.time}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            selectedTask?.let {
                IconButton(
                    onClick = {
                        viewModel.deleteTask(it)
                        selectedTask = null
                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .background(Color.Red, shape = CircleShape)
                        .size(56.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Borrar Tarea", tint = Color.White)
                }
            }
        }
    }
}
