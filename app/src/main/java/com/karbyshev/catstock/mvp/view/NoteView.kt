package com.karbyshev.catstock.mvp.view

import com.arellomobile.mvp.MvpView
import com.karbyshev.catstock.mvp.model.NetworkItem

interface NoteView : MvpView {

    fun showNote(note: NetworkItem)

    fun onNoteSaved()

    fun onNoteDeleted()

    fun showNoteInfoDialog(noteInfo: String)

    fun hideNoteInfoDialog()

    fun showNoteDeleteDialog()

    fun hideNoteDeleteDialog()
}