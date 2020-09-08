package com.example.notemvpkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notemvpkotlin.R
import com.example.notemvpkotlin.model.Note
import kotlinx.android.synthetic.main.note_item.view.*
import kotlinx.coroutines.GlobalScope

class NotesAdapter:
    androidx.recyclerview.widget.ListAdapter<Note, NotesAdapter.NotesViewHolder>(diffCallback) {

    interface OnNoteClickListener {
        fun onNoteClick(note: Note?)
        fun onNoteLongClick(note: Note?)
    }

    private var onNoteClickListener: OnNoteClickListener? = null

    fun setOnNoteClickListener(onNoteClickListener: OnNoteClickListener?) {
        this.onNoteClickListener = onNoteClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item,
            parent, false))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
//        val note: Note = getItem(position)
//        holder.title.text = note.title
//        holder.date.text = note.date
        getItem(position).let { note ->
            holder.title.text = note.title
            holder.date.text = note.date
        }
    }

    inner class NotesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.textViewNoteTitle
        val date: TextView = view.textViewNoteData
        init {
            view.setOnClickListener {
                onNoteClickListener?.onNoteClick(getItem(adapterPosition))
            }
            view.setOnLongClickListener {
                onNoteClickListener?.onNoteLongClick(getItem(adapterPosition))
                true
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title &&
                        oldItem.description == newItem.description &&
                        oldItem.date == newItem.date
            }
        }
    }
}