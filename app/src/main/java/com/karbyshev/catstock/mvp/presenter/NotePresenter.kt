package com.karbyshev.catstock.mvp.presenter

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.karbyshev.catstock.App
import com.karbyshev.catstock.mvp.model.Item
import com.karbyshev.catstock.mvp.model.ItemDao
import com.karbyshev.catstock.mvp.view.NoteView
import com.karbyshev.catstock.ui.common.ImageUtils
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*
import javax.inject.Inject

@InjectViewState
class NotePresenter(private val noteId: Long, context: Context) : MvpPresenter<NoteView>() {

    @Inject
    lateinit var noteDao: ItemDao
    private lateinit var note: Item
    private var imageUtils = ImageUtils(context)
    private var imagePath = ""

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
        note.image = imagePath

        noteDao.saveNote(note)
        viewState.onNoteSaved()
    }

    fun deleteNote() {
        val noteId = note.id
        noteDao.deleteNote(note)
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

    fun addImage(data: Intent?, imageView: ImageView) {
        val contentUri = data!!.data
        val file = File(imageUtils.getRealPathFromURI(contentUri))
        ImageUtils.savedImagePath = file.absolutePath
        imagePath = ImageUtils.fileUriPrefix + ImageUtils.savedImagePath
        Picasso.get().load(imagePath).into(imageView)
    }

}