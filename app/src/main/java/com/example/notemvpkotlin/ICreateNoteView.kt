package com.example.notemvpkotlin

import com.example.notemvpkotlin.model.Note

interface ICreateNoteView {
    fun goHome()
    fun getTitleFromEditText(): String?
    fun getDescriptionFromEditText(): String?
    fun showMsgFailValid()
    fun showSuccessful()
    fun initComponents(note: Note?)
    fun newNoteForEquals(): Note?
}