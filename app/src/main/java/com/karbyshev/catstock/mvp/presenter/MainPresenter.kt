package com.karbyshev.catstock.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.karbyshev.catstock.App
import com.karbyshev.catstock.R.id.notesList
import com.karbyshev.catstock.mvp.model.ItemDao
import com.karbyshev.catstock.mvp.model.NetworkItem
import com.karbyshev.catstock.mvp.view.MainView
import com.karbyshev.catstock.network.RequestNotes
import com.karbyshev.catstock.network.RetrofitService
import com.karbyshev.catstock.util.getNotesSortMethodName
import com.karbyshev.catstock.util.setNotesSortMethod
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    enum class SortNotesBy : Comparator<NetworkItem> {
        DATE {
            override fun compare(note1: NetworkItem, note2: NetworkItem) = note2.updatedAt.compareTo(note1.updatedAt)
        },
        NAME {
            override fun compare(note1: NetworkItem, note2: NetworkItem) = note1.title.compareTo(note2.title)
        },
    }

    @Inject
    lateinit var noteDao: ItemDao
    private var notesList = mutableListOf<NetworkItem>()
    private var noteRequest = CompositeDisposable()

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

//        val note = notesList[position]
//        noteDao.deleteNote(note)
//        notesList.remove(note)
//        viewState.onNoteDeleted()
    }

    fun openNewNote() {
        val note = NetworkItem("","", "")
        noteRequest.add(RequestNotes.createNote(note).subscribe {
            viewState.openNoteScreen(it.id)
        })

//        val newNote = noteDao.createNote()
//        viewState.openNewNoteScreen()
    }

    fun openNote(position: Int) {
        noteRequest.add(RequestNotes.getNotes().subscribe{
            viewState.openNoteScreen(it[position].id)
        })
    }

    fun search(query: String) {
        if (query == "") {
            viewState.onSearchResult(notesList)
        } else {
            val searchResults = notesList.filter { it.title.startsWith(query, ignoreCase = true) }
            viewState.onSearchResult(searchResults)
        }
    }

    fun sortNotesBy(sortMethod: MainPresenter.SortNotesBy) {
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
//        viewState.showNoteInfoDialog(notesList[position].getInfo())
    }

    fun hideNoteInfoDialog() {
        viewState.hideNoteInfoDialog()
    }

    fun loadAllNotes() {

        noteRequest.add(RequestNotes.getNotes().subscribe {
            viewState.onNotesLoaded(it)
        })

//        notesList = noteDao.loadAllNotes()
//        Collections.sort(notesList, getCurrentSortMethod())
//        viewState.onNotesLoaded(notesList)
    }

    private fun getCurrentSortMethod(): MainPresenter.SortNotesBy {
        val defaultSortMethodName = SortNotesBy.DATE.toString()
        val currentSortMethodName = getNotesSortMethodName(defaultSortMethodName)
        return SortNotesBy.valueOf(currentSortMethodName)
    }

    private fun getNotePositionById(noteId: Long) = notesList.indexOfFirst { it.id == noteId }
}
