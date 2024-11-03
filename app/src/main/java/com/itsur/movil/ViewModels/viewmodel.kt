package com.itsur.movil.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.itsur.movil.DataBase.Note
import com.itsur.movil.DataBase.NoteDatabase
import com.itsur.movil.DataBase.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
        private val repository: NoteRepository

        init {
                val noteDao = NoteDatabase.getDatabase(application).noteDao()
                repository = NoteRepository(noteDao)
        }

        val notes: LiveData<List<Note>> = repository.getAllNotes().asLiveData()

        fun addOrUpdate(note: Note) {
                viewModelScope.launch {
                        repository.addOrUpdate(note)
                }
        }

        suspend fun getNoteById(id: Int): Note? {
                return repository.getNoteById(id)
        }

        fun delete(noteId: Int) {
                viewModelScope.launch {
                        repository.delete(noteId)
                }
        }
}
