package com.karbyshev.catstock.di.module

import com.karbyshev.catstock.mvp.model.ItemDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ItemDaoModule {

    @Provides
    @Singleton
    fun provideItemDao(): ItemDao = ItemDao()
}