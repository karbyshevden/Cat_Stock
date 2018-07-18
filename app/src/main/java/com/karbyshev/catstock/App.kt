package com.karbyshev.catstock

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.karbyshev.catstock.di.component.AppComponent
import com.karbyshev.catstock.di.component.DaggerAppComponent
import com.karbyshev.catstock.di.module.ItemDaoModule
import com.karbyshev.catstock.mvp.model.AppDatabase
import com.karbyshev.catstock.mvp.model.Item
import com.karbyshev.catstock.network.RetrofitService
import com.reactiveandroid.ReActiveAndroid
import com.reactiveandroid.ReActiveConfig
import com.reactiveandroid.internal.database.DatabaseConfig

class App: Application() {

    companion object {
        lateinit var graph: AppComponent
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }



    override fun onCreate() {
        super.onCreate()

        context = this

        graph = DaggerAppComponent.builder().itemDaoModule(ItemDaoModule()).build()

        val appDatabaseConfig = DatabaseConfig.Builder(AppDatabase::class.java)
                .addModelClasses(Item::class.java)
                .build()

        ReActiveAndroid.init(ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabaseConfig)
                .build())

        RetrofitService.init()

//        ApiService.createInterface()
    }
}