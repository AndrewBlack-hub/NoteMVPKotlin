package com.example.notemvpkotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notemvpkotlin.model.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}