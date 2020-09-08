package com.example.notemvpkotlin.presenters

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.example.notemvpkotlin.model.Note

interface IMainPresenter {
    fun createBundleForNote(note: Note?): Bundle?
    fun deleteNote(note: Note?)
    fun insertNote(note: Note?)
    fun createLocaleNote(note: Note?): Note?
    fun getData(): LiveData<List<Note>>?
}