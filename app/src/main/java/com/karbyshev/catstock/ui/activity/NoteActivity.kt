package com.karbyshev.catstock.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.karbyshev.catstock.R
import com.karbyshev.catstock.mvp.model.Item
import com.karbyshev.catstock.mvp.presenter.NotePresenter
import com.karbyshev.catstock.mvp.view.NoteView
import com.karbyshev.catstock.util.formatDate
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : MvpAppCompatActivity(), NoteView {

    companion object {
        const val NOTE_DELETE_ARG = "note_id"

        fun buildIntent(activity: Activity, noteId: Long) : Intent {
            val intent = Intent(activity, NoteActivity::class.java)
            intent.putExtra(NOTE_DELETE_ARG, noteId)
            return intent
        }
    }

    @InjectPresenter
    lateinit var presenter: NotePresenter
    private var noteDeleteDialog: MaterialDialog? = null
    private var noteInfoDialog: MaterialDialog? = null

    @ProvidePresenter
    fun provideHelloPresenter(): NotePresenter {
        val noteId = intent.extras.getLong(NOTE_DELETE_ARG, -1)
        return NotePresenter(noteId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        noteTitleEditText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val editText = view as EditText
                editText.setSelection((editText.text.length))
            }
        }
    }

    override fun onStop() {
        super.onStop()

        if (noteTitleEditText.text.isEmpty()){
            presenter.deleteNote()
        } else {
            presenter.saveNote(noteTitleEditText.text.toString(), noteTextEditText.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (noteTitleEditText.text.isEmpty()){
            presenter.deleteNote()
        } else {
            presenter.saveNote(noteTitleEditText.text.toString(), noteTextEditText.text.toString())
        }
    }

    override fun showNote(note: Item) {
        noteDateTextView.text = formatDate(note.changedAt)
        noteTitleEditText.setText(note.title)
        noteTextEditText.setText(note.text)
    }

    override fun showNoteInfoDialog(noteInfo: String) {
        noteInfoDialog = MaterialDialog.Builder(this)
                .title(R.string.note_info)
                .positiveText(R.string.ok)
                .content(noteInfo)
                .onPositive { materialDialog, dialogAction -> presenter.hideNoteInfoDialog() }
                .cancelListener { presenter.hideNoteInfoDialog() }
                .show()
    }

    override fun hideNoteInfoDialog() {
        noteInfoDialog?.dismiss()
    }

    override fun showNoteDeleteDialog() {
        noteDeleteDialog = MaterialDialog.Builder(this)
                .title(getString(R.string.note_deletion_title))
                .content(getString(R.string.note_deletion_message))
                .positiveText(getString(R.string.yes))
                .negativeText(getString(R.string.no))
                .onPositive { _, _ ->
                    presenter.hideNoteDeleteDialog()
                    presenter.deleteNote()
                }
                .onNegative { _, _ -> presenter.hideNoteDeleteDialog() }
                .cancelListener { presenter.hideNoteDeleteDialog() }
                .show()
    }


    override fun hideNoteDeleteDialog() {
        noteDeleteDialog?.dismiss()
    }

    override fun onNoteSaved() {
        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
    }

    override fun onNoteDeleted() {
        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (noteTitleEditText.text.isEmpty()){
            presenter.deleteNote()
        } else {
            presenter.saveNote(noteTitleEditText.text.toString(), noteTextEditText.text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuNoteInfo -> presenter.showNoteInfoDialog()
        }
        return super.onOptionsItemSelected(item)
    }
}
