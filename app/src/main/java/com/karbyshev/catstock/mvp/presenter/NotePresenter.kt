package com.karbyshev.catstock.mvp.presenter

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.karbyshev.catstock.App
import com.karbyshev.catstock.mvp.model.ItemDao
import com.karbyshev.catstock.mvp.model.NetworkItem
import com.karbyshev.catstock.mvp.view.NoteView
import com.karbyshev.catstock.network.RequestNotes
import com.karbyshev.catstock.ui.activity.NoteActivity.Companion.imagePath
import com.karbyshev.catstock.util.ImageUtils
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import javax.inject.Inject

@InjectViewState
class NotePresenter(private val noteId: Long, context: Context) : MvpPresenter<NoteView>() {

    @Inject
    lateinit var noteDao: ItemDao
    private lateinit var note: NetworkItem
    private var imageUtils = ImageUtils(context)
    private var noteRequest = CompositeDisposable()

    init {
        App.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        noteRequest.add(RequestNotes.getNoteById(noteId).subscribe {
            viewState.showNote(it)
        })

//        note = noteDao.getNoteById(noteId)!!
//        viewState.showNote(note)
    }

    fun saveNote(title: String, text: String, image: String) {
        note = NetworkItem(title, text, image)
        noteRequest.add(RequestNotes.saveNote(noteId, note).subscribe {
            viewState.onNoteSaved()
        })

//        note.title = title
//        note.content = text
//        note.updatedAt = Date()
//        note.image = imagePath
//        noteDao.saveNote(note)
    }

    fun deleteNote() {
        noteRequest.add(RequestNotes.deleteNote(noteId).subscribe {
            viewState.onNoteDeleted()
        })

//        noteDao.deleteNote(note)
    }

    fun showNoteDeleteDialog() {
        viewState.showNoteDeleteDialog()
    }

    fun hideNoteDeleteDialog() {
        viewState.hideNoteDeleteDialog()
    }

    fun showNoteInfoDialog() {
//        viewState.showNoteInfoDialog(note.getInfo())
    }

    fun hideNoteInfoDialog() {
        viewState.hideNoteInfoDialog()
    }

    fun addImage(data: Intent?, imageView: ImageView, contentResolver: ContentResolver) {
        val contentUri = data!!.data
//        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, contentUri)
//        imageView.setImageBitmap(bitmap)
        val file = File(imageUtils.getRealPathFromURI(contentUri))
        ImageUtils.savedImagePath = file.absolutePath
        val temp = ImageUtils.fileUriPrefix + ImageUtils.savedImagePath
        imagePath = ImageUtils.fileUriPrefix + ImageUtils.savedImagePath
    }

}