package com.example.notemvpkotlin

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.notemvpkotlin.model.Note
import com.example.notemvpkotlin.presenters.CreateFragmentPresenter
import com.example.notemvpkotlin.presenters.ICreateNotePresenter
import com.example.notemvpkotlin.presenters.MainFragmentPresenter
import kotlinx.android.synthetic.main.fragment_create_note.*

class CreateNoteFragment : Fragment(), ICreateNoteView {

    private var note: Note? = null
    private val presenter: ICreateNotePresenter = CreateFragmentPresenter(this)
    private var title: EditText? = null
    private var description: EditText? = null
    private var dateOfChange: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view: View? = inflater.inflate(R.layout.fragment_create_note, container, false)
        title = view?.findViewById(R.id.et_title)
        description = view?.findViewById(R.id.et_description)
        dateOfChange = view?.findViewById(R.id.tv_date_of_change)
        val bundle = arguments
        if (bundle != null) {
            note = bundle.getParcelable(MainFragmentPresenter.BUNDLE_KEY)
            initComponents(note)
            dateOfChange?.visibility = View.VISIBLE
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (note != null) {
                    if (!(newNoteForEquals()?.equals(startNote(note)))!!) {
                        presenter.createAlertDialog(context)
                    } else {
                        goHome()
                    }
                } else {
                    goHome()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun startNote(note: Note?) : Note? {
        return note
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val title = et_title.text.toString()
        val description = et_description.text.toString()
        if (item.itemId == R.id.action_save_note) {
            if (note != null) {
                if (presenter.validation(title, description)) {
                    presenter.updateNote(Note(note?.id, title, description, presenter.date()))
                    goHome()
                } else {
                    showMsgFailValid()
                }
            } else {
                if (presenter.validation(title, description)) {
                    presenter.clickSaveNote(title, description)
                    showSuccessful()
                    goHome()
                } else {
                    showMsgFailValid()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun goHome() {
        Navigation.findNavController(requireView()).navigate(R.id.home_dest)
    }

    override fun getTitleFromEditText(): String? {
        return et_title.text.toString()
    }

    override fun getDescriptionFromEditText(): String? {
        return et_description.text.toString()
    }

    override fun showMsgFailValid() {
        Toast.makeText(context, getString(R.string.enter_all_fields), Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessful() {
        Toast.makeText(context, R.string.successful_saving, Toast.LENGTH_SHORT).show()
    }

    override fun initComponents(note: Note?) {
        title?.setText(note?.title)
        description?.setText(note?.description)
        dateOfChange?.text = String.format(getString(R.string.date_of_update), note?.date)
    }

    override fun newNoteForEquals(): Note? {
        return Note(note?.id, et_title.text.toString(), et_description.text.toString(), note?.date)
    }
}