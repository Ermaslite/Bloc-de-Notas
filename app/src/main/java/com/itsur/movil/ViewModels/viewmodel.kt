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
import androidx.lifecycle.*

class NoteViewModel(application: Application) : AndroidViewModel(application) {
        private val repository: NoteRepository

        init {
                val noteDao = NoteDatabase.getDatabase(application).noteDao()
                repository = NoteRepository(noteDao)
        }

        val notes: LiveData<List<Note>> = repository.getAllNotes().asLiveData()

        private val _note = MutableLiveData<Note?>()
        val note: LiveData<Note?> get() = _note

        fun getNoteById(id: Int) {
                viewModelScope.launch {
                        _note.value = repository.getNoteById(id)
                }
        }

        fun addOrUpdate(note: Note) {
                viewModelScope.launch {
                        repository.addOrUpdate(note)
                }
        }

        fun delete(noteId: Int) {
                viewModelScope.launch {
                        repository.delete(noteId)
                }
        }
}

