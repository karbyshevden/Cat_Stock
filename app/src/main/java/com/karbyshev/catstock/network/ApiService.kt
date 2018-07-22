package com.karbyshev.catstock.network



import com.karbyshev.catstock.mvp.model.NetworkItem
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("notes")
    fun getAllNotes(): Observable<List<NetworkItem>>

    @POST("notes")
    fun createNote(@Body networkItem: NetworkItem): Observable<NetworkItem>

    @GET("notes/{id}")
    fun getNoteById(@Path("id") id: Long): Observable<NetworkItem>

    @PUT("notes/{id}")
    fun updateNote(@Path("id") id: Long, @Body networkItem: NetworkItem): Observable<NetworkItem>

    @DELETE("notes/{id}")
    fun deleteNote(@Path("id") id: Long) : Observable<Response<Void>>
}