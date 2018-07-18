package com.karbyshev.catstock.mvp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.karbyshev.catstock.util.formatDate
import com.reactiveandroid.Model
import com.reactiveandroid.annotation.Column
import com.reactiveandroid.annotation.PrimaryKey
import com.reactiveandroid.annotation.Table
import java.util.*

@Table(name = "Notes", database = AppDatabase::class)
data class Item(

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Long = 0,

    @Column(name = "title")
    @SerializedName("title")
    @Expose
    var title: String? = null,

    @Column(name = "text")
    @SerializedName("content")
    @Expose
    var text: String? = null,

    @Column(name = "image")
    @SerializedName("image")
    @Expose
    var image: String = "",

    @Column(name = "created_at")
    @SerializedName("createdAt")
    @Expose
    var createdAt: Date? = null,

    @Column(name = "change_at")
    @SerializedName("changedAt")
    @Expose
    var changedAt: Date? = null) : Model() {

    constructor(title: String, createDate: Date) : this(0, "", "", "", Date(), Date()) {
        this.title = title
        this.createdAt = createDate
        this.changedAt = createDate
    }

    fun getInfo(): String = "Title: \n$title\n" +
            "Created at: \n${formatDate(createdAt)}\n" +
            "Changed at: \n${formatDate((changedAt))}\n"
}