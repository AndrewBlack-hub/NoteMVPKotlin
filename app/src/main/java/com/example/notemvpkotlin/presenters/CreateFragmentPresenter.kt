package com.example.notemvpkotlin.presenters

import android.app.AlertDialog
import android.content.Context
import com.example.notemvpkotlin.ICreateNoteView
import com.example.notemvpkotlin.R
import com.example.notemvpkotlin.data.App
import com.example.notemvpkotlin.model.Note
import java.text.DateFormat
import java.util.*

class CreateFragmentPresenter(private val view: ICreateNoteView?) : ICreateNotePresenter {

    private val notesDao = App.getInstance()?.getDatabase()?.notesDao()

    override fun saveNote(note: Note?) {
        notesDao?.insertNote(note)
    }

    override fun updateNote(note: Note?) {
        notesDao?.updateNote(note)
    }

    override fun createAlertDialog(context: Context?) {
        val builder = AlertDialog.Builder(context)
            .setMessage(R.string.save_the_changes)
            .setCancelable(false)
            .setPositiveButton(
                R.string.yes
            ) { _, _ ->
                if (validation(view?.getTitleFromEditText(), view?.getDescriptionFromEditText())) {
                    notesDao?.updateNote(view?.newNoteForEquals())
                    view?.goHome()
                } else {
                    view?.showMsgFailValid()
                }
            }
        builder.setNegativeButton(R.string.no) { _, _ ->
            view?.goHome()
        }.create().show()
    }

    override fun validation(title: String?, description: String?): Boolean {
        return !(title!!.isEmpty() || description!!.isEmpty())
    }

    override fun clickSaveNote(title: String?, description: String?) {
        saveNote(Note(title, description, date()))
    }

    override fun date(): String? {
        val currentDate = Calendar.getInstance().time
        return DateFormat.getDateInstance().format(currentDate)
    }
}