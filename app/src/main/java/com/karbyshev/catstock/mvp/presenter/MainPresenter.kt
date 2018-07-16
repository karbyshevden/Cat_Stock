package com.karbyshev.catstock.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.karbyshev.catstock.App
import com.karbyshev.catstock.mvp.model.Item
import com.karbyshev.catstock.mvp.model.ItemDao
import com.karbyshev.catstock.mvp.view.MainView
import com.karbyshev.catstock.util.getNotesSortMethodName
import com.karbyshev.catstock.util.setNotesSortMethod
import java.util.*
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    enum class SortNotesBy : Comparator<Item> {
        DATE {
            override fun compare(note1: Item, note2: Item) = note2.changedAt!!.compareTo(note1.changedAt)
        },
        NAME {
            override fun compare(note1: Item, note2: Item) = note1.title!!.compareTo(note2.title!!)
        },
    }

    @Inject
    lateinit var noteDao: ItemDao
    private lateinit var notesList: MutableList<Item>

    init {
        App.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadAllNotes()
    }

    fun deleteAllNotes() {
        noteDao.deleteAllNotes()
        notesList.removeAll(notesList)
        viewState.onAllNotesDeleted()
    }

    fun deleteNoteByPosition(position: Int) {
        val note = notesList[position]
        noteDao.deleteNote(note)
        notesList.remove(note)
        viewState.onNoteDeleted()
    }

    fun openNewNote() {
        val newNote = noteDao.createNote()
        viewState.openNoteScreen(newNote.id)
    }

    fun openNote(position: Int) {
        viewState.openNoteScreen(notesList[position].id)
    }

    fun search(query: String) {
        if (query == "") {
            viewState.onSearchResult(notesList)
        } else {
            val searchResults = notesList.filter { it.title!!.startsWith(query, ignoreCase = true) }
            viewState.onSearchResult(searchResults)
        }
    }

    fun sortNotesBy(sortMethod: SortNotesBy) {
        notesList.sortWith(sortMethod)
        setNotesSortMethod(sortMethod.toString())
        viewState.updateView()
    }

    fun showNoteContextDialog(position: Int) {
        viewState.showNoteContextDialog(position)
    }

    fun hideNoteContextDialog() {
        viewState.hideNoteContextDialog()
    }

    fun showNoteDeleteDialog(position: Int) {
        viewState.showNoteDeleteDialog(position)
    }

    fun hideNoteDeleteDialog() {
        viewState.hideNoteDeleteDialog()
    }

    fun showNoteInfo(position: Int) {
        viewState.showNoteInfoDialog(notesList[position].getInfo())
    }

    fun hideNoteInfoDialog() {
        viewState.hideNoteInfoDialog()
    }

    fun loadAllNotes() {
        notesList = noteDao.loadAllNotes()
        Collections.sort(notesList, getCurrentSortMethod())
        viewState.onNotesLoaded(notesList)
    }

    private fun getCurrentSortMethod(): SortNotesBy {
        val defaultSortMethodName = SortNotesBy.DATE.toString()
        val currentSortMethodName = getNotesSortMethodName(defaultSortMethodName)
        return SortNotesBy.valueOf(currentSortMethodName)
    }

    private fun getNotePositionById(noteId: Long) = notesList.indexOfFirst { it.id == noteId }
}