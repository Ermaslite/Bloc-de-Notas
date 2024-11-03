package com.itsur.movil.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.itsur.movil.DataBase.Task
import com.itsur.movil.DataBase.TaskDatabase
import com.itsur.movil.DataBase.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskRepository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        taskRepository = TaskRepository(taskDao)
        allTasks = taskRepository.allTasks
    }

    fun getTask(taskId: Int): LiveData<Task> {
        return taskRepository.getTask(taskId)
    }

    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.update(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.delete(task)
    }
}
