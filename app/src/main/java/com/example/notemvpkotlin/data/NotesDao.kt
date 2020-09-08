package com.example.notemvpkotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notemvpkotlin.model.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    fun insertNote(note: Note?)

    @Delete
    fun deleteNote(note: Note?)

    @Update
    fun updateNote(note: Note?)
}