package com.example.notemvpkotlin.presenters

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.example.notemvpkotlin.data.NotesDao
import com.example.notemvpkotlin.model.Note

class MainFragmentPresenter(private val notesDao: NotesDao?) : IMainPresenter {

    override fun getData(): LiveData<List<Note>>? {
        return notesDao?.getAllNotes()
    }

    companion object {
        const val BUNDLE_KEY = "note"
    }

    override fun createBundleForNote(note: Note?): Bundle? {
        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_KEY, note)
        return bundle
    }

    override fun deleteNote(note: Note?) {
        notesDao?.deleteNote(note!!)
    }

    override fun insertNote(note: Note?) {
        notesDao?.insertNote(note!!)
    }

    override fun createLocaleNote(note: Note?): Note? {
        return note?.id?.let { Note(it, note.title, note.description, note.date) }
    }
}