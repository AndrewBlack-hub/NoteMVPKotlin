package com.example.notemvpkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notemvpkotlin.adapter.NotesAdapter
import com.example.notemvpkotlin.data.App
import com.example.notemvpkotlin.model.Note
import com.example.notemvpkotlin.presenters.IMainPresenter
import com.example.notemvpkotlin.presenters.MainFragmentPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var adapter: NotesAdapter = NotesAdapter()
    private val presenter: IMainPresenter = MainFragmentPresenter(
        App.getInstance()?.getDatabase()?.notesDao()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview_main_fragment.layoutManager = LinearLayoutManager(context)
        recyclerview_main_fragment.adapter = adapter
        presenter.getData()?.observe(viewLifecycleOwner,
            Observer<List<Note>> { notesFromLiveData ->
                adapter.submitList(notesFromLiveData)
                if (notesFromLiveData.isNotEmpty()) {
                    view.findViewById<View>(R.id.iv_empty_note_pic).visibility = View.GONE
                } else {
                    view.findViewById<View>(R.id.iv_empty_note_pic).visibility = View.VISIBLE
                }
            })

        adapter.setOnNoteClickListener(object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(note: Note?) {
                Navigation.findNavController(view).navigate(R.id.create_note_dest,
                    presenter.createBundleForNote(note))
            }

            override fun onNoteLongClick(note: Note?) {
                val localNote: Note? = presenter.createLocaleNote(note)
                presenter.deleteNote(note)
                val snackbar = Snackbar.make(view, getString(R.string.restore_a_note),
                    Snackbar.LENGTH_LONG)
                    .setAction(R.string.restore) {
                        presenter.insertNote(localNote)
                        Snackbar.make(view, getString(R.string.note_restored),
                            Snackbar.LENGTH_SHORT).show()
                    }
                snackbar.show()
            }
        })

        create_note_fab.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.create_note_dest)
        }

        recyclerview_main_fragment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when {
                    dy > 0 -> {
                        create_note_fab.hide()
                    }
                    dy < 0 -> {
                        create_note_fab.show()
                    }
                    else -> {
                        create_note_fab.show()
                    }
                }
            }
        })
    }
}