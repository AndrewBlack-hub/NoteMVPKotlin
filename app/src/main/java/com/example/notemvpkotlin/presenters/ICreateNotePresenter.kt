package com.example.notemvpkotlin.presenters

import android.content.Context
import com.example.notemvpkotlin.model.Note

interface ICreateNotePresenter {
    fun saveNote(note: Note?)
    fun updateNote(note: Note?)
    fun createAlertDialog(context: Context?)
    fun validation(title: String?, description: String?): Boolean
    fun clickSaveNote(title: String?, description: String?)
    fun date(): String?
}