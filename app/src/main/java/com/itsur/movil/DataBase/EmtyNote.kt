package com.itsur.movil.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val mediaUris: List<String> = emptyList(), // Listado de URIs de multimedia
    val timestamp: Long = System.currentTimeMillis()
)
