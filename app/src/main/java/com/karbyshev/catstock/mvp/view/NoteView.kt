package com.karbyshev.catstock.mvp.view

import com.arellomobile.mvp.MvpView
import com.karbyshev.catstock.mvp.model.Item

interface NoteView : MvpView {

    fun showNote(note: Item)

    fun onNoteSaved()

    fun onNoteDeleted()

    fun showNoteInfoDialog(noteInfo: String)

    fun hideNoteInfoDialog()

    fun showNoteDeleteDialog()

    fun hideNoteDeleteDialog()
}