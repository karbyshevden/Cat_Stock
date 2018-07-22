package com.karbyshev.catstock.network


import com.karbyshev.catstock.mvp.model.NetworkItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.Android
import retrofit2.Response

class RequestNotes {
    companion object {
        fun getNotes(): Observable<List<NetworkItem>> {
            return RetrofitService.apiImpl.getAllNotes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun getNoteById(id: Long): Observable<NetworkItem> {
            return RetrofitService.apiImpl.getNoteById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun createNote(networkItem: NetworkItem): Observable<NetworkItem> {
            return RetrofitService.apiImpl.createNote(networkItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun saveNote(id: Long, networkItem: NetworkItem): Observable<NetworkItem> {
            return RetrofitService.apiImpl.updateNote(id, networkItem)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

        fun deleteNote(id: Long): Observable<Response<Void>> {
            return RetrofitService.apiImpl.deleteNote(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}