package com.itsur.movil.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itsur.movil.Models.Converters

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
@Database(entities = [Note::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Añade la nueva columna 'mediaUris' a la tabla 'notes'
                database.execSQL("ALTER TABLE notes ADD COLUMN mediaUris TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Añade la nueva columna 'reminders' a la tabla 'notes'
                database.execSQL("ALTER TABLE notes ADD COLUMN reminders TEXT NOT NULL DEFAULT '[]'")
            }
        }

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Añadir las migraciones aquí
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
