package com.karbyshev.catstock.network


import com.karbyshev.catstock.mvp.model.Item
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @GET("notes")
    fun getAllNotes(): Observable<List<Item>>

    @POST("notes")
    fun createNote(): Observable<Item>

    @GET("notes/{id}")
    fun getNoteById(@Path("id") id: Long): Observable<Item>

    @PUT("notes/{id}")
    fun updateNote(@Path("id") id: Long): Observable<Item>

    @DELETE("notes/{id}")
    fun deleteNote(@Path("id") id: Long) : Observable<Item>
}