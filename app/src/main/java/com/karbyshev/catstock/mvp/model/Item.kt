package com.karbyshev.catstock.mvp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.karbyshev.catstock.util.formatDate
import com.reactiveandroid.Model
import com.reactiveandroid.annotation.Column
import com.reactiveandroid.annotation.PrimaryKey
import com.reactiveandroid.annotation.Table
import java.io.Serializable
import java.util.*

@Table(name = "Notes", database = AppDatabase::class)
data class Item(

        @PrimaryKey
        var id: Long,

        @Column(name = "title")
        var title: String,

        @Column(name = "content")
        var content: String,

        @Column(name = "image")
        var image: String = "",

        @Column(name = "created_at")
        var createdAt: Date,//Date? = null,

        @Column(name = "change_at")
        var updatedAt: Date) : Model() {//Date? = null) : Model() {


    constructor(title: String, createDate: Date) : this(0, "", "", "", Date(), Date()) {//Date(), Date()) {
        this.title = title
        this.createdAt = createDate
        this.updatedAt = createDate
    }

    fun getInfo(): String = "Title: \n$title\n" +
            "Created at: \n${formatDate(createdAt)}\n" +
            "Changed at: \n${formatDate((updatedAt))}\n"
}