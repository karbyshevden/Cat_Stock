package com.karbyshev.catstock.mvp.model


import com.reactiveandroid.query.Delete
import com.reactiveandroid.query.Select
import java.util.*

class ItemDao {

    fun createNote(): Item {
        val note = Item("", Date())
        note.save()
        return note
    }

    fun saveNote(note: Item): Long = note.save()

    fun loadAllNotes(): MutableList<Item> = Select.from(Item::class.java).fetch()

    fun getNoteById(noteId: Long): Item? = Select.from(Item::class.java).where("id = ?", noteId).fetchSingle()

    fun deleteAllNotes() = Delete.from(Item::class.java).execute()

    fun deleteNote(note: Item) = note.delete()
}

