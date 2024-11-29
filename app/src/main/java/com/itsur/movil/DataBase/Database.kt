package com.itsur.movil.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itsur.movil.Models.Converters

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.itsur.movil.alarm.AlarmItem
@Database(entities = [Note::class, AlarmItem::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notes ADD COLUMN mediaUris TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE notes ADD COLUMN reminders TEXT NOT NULL DEFAULT '[]'")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // migración a la versión 4 si aplica
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Crear la nueva tabla con la columna `alarmTimestamp`
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `alarms_new` (
                        `alarmId` INTEGER NOT NULL, 
                        `alarmTimestamp` LONG NOT NULL, 
                        `message` TEXT NOT NULL, 
                        PRIMARY KEY(`alarmId`)
                    )
                """.trimIndent())

                // Copiar los datos de la tabla antigua a la nueva tabla
                database.execSQL("""
                    INSERT INTO `alarms_new` (`alarmId`, `alarmTimestamp`, `message`)
                    SELECT `alarmId`, (`strftime('%s', `alarmDate` || ' ' || `alarmTime`) * 1000), `message`
                    FROM `alarms`
                """.trimIndent())

                // Eliminar la tabla antigua
                database.execSQL("DROP TABLE `alarms`")

                // Renombrar la nueva tabla a la nombre antiguo
                database.execSQL("ALTER TABLE `alarms_new` RENAME TO `alarms`")
            }
        }

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Aquí se pueden realizar otras migraciones necesarias
                // En este caso, no hay cambios adicionales que necesitemos hacer
            }
        }

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
