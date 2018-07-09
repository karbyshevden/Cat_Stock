package com.karbyshev.catstock.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.karbyshev.catstock.util.formatDate
import java.util.*

@Entity
class Item {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var title: String = ""

    var text: String = ""

    var createAt: Date? = null

    var changedAt: Date? = null

    constructor(title: String, createDate: Date?) {
        this.title = title
        this.createAt = createDate
        this.changedAt = createDate
    }

    constructor()

    fun getInfo(): String = "Title: \n$title\n" +
            "Created at: \n${formatDate(createAt)}\n" +
            "Changed at: \n${formatDate((changedAt))}\n"
}