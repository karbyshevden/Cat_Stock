package com.karbyshev.catstock.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query
import java.util.*

@Dao
interface ItemDao{

    @Query("SELECT * FROM item")
    fun loadAllNotes()

    @Query("DELETE FROM item")
    fun clearTable()

    @Delete
    fun deleteAllNotes()
}