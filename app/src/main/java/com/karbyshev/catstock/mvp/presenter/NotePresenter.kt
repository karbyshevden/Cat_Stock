package com.karbyshev.catstock.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.karbyshev.catstock.App
import com.karbyshev.catstock.bus.NoteDeleteAction
import com.karbyshev.catstock.bus.NoteEditAction
import com.karbyshev.catstock.mvp.model.Item
import com.karbyshev.catstock.mvp.model.ItemDao
import com.karbyshev.catstock.mvp.view.NoteView
import org.greenrobot.eventbus.EventBus
import java.util.*
import javax.inject.Inject

@InjectViewState
class NotePresenter(private val noteId: Long) : MvpPresenter<NoteView>() {

    @Inject
    lateinit var noteDao: ItemDao
    private lateinit var note: Item

    init {
        App.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        note = noteDao.getNoteById(noteId)!!
        viewState.showNote(note)
    }

    fun saveNote(title: String, text: String) {
        note.title = title
        note.text = text
        note.changedAt = Date()
        noteDao.saveNote(note)
        EventBus.getDefault().post(NoteEditAction(note.id))
        viewState.onNoteSaved()
    }

    fun deleteNote() {
        val noteId = note.id
        noteDao.deleteNote(note)
        EventBus.getDefault().post(NoteDeleteAction(noteId))
        viewState.onNoteDeleted()
    }

    fun showNoteDeleteDialog() {
        viewState.showNoteDeleteDialog()
    }

    fun hideNoteDeleteDialog() {
        viewState.hideNoteDeleteDialog()
    }

    fun showNoteInfoDialog() {
        viewState.showNoteInfoDialog(note.getInfo())
    }

    fun hideNoteInfoDialog() {
        viewState.hideNoteInfoDialog()
    }

}