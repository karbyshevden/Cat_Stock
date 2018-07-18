package com.karbyshev.catstock.network

import com.karbyshev.catstock.mvp.model.Item
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.Android

class RequestNotes {
    companion object {
        fun getNotes(): Observable<List<Item>> {
            return RetrofitService.apiImpl.getAllNotes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}