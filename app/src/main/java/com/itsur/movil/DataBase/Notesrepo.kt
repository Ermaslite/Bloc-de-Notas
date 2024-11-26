package com.itsur.movil.DataBase

import kotlinx.coroutines.flow.Flow
class NoteRepository(private val noteDao: NoteDao) {
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun addOrUpdate(note: Note) {
        if (note.id == 0) {
            noteDao.insert(note)
        } else {
            noteDao.update(note)
        }
    }

    suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }

    suspend fun delete(noteId: Int) {
        val note = getNoteById(noteId)
        if (note != null) {
            noteDao.delete(note)
        }
    }
}


