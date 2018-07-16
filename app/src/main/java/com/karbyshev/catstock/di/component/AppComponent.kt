package com.karbyshev.catstock.di.component


import com.karbyshev.catstock.di.module.ItemDaoModule
import com.karbyshev.catstock.mvp.presenter.MainPresenter
import com.karbyshev.catstock.mvp.presenter.NotePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ItemDaoModule::class])
interface AppComponent {

    fun inject(mainPresenter: MainPresenter)

    fun inject(notePresenter: NotePresenter)
}