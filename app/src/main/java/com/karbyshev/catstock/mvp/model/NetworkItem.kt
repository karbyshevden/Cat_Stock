package com.karbyshev.catstock.mvp.model

data class NetworkItem(
        val id: Long,
        var title: String,
        var content: String,
        var image: String,
        val createdAt: String,
        val updatedAt: String) {

    constructor(title: String, content: String, image: String) : this(0, "","",  "", "", "") {
        this.title = title
        this.content = content
        this.image = image
    }

    constructor() : this(0, "", "", "", "", "")
}