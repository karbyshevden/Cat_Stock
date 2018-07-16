package com.karbyshev.catstock.util

import android.content.Context
import com.karbyshev.catstock.App

private val prefs by lazy {
    App.context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
}

fun getNotesSortMethodName(defaultValue: String): String = prefs.getString("sort_method", defaultValue)

fun setNotesSortMethod(sortMethod: String) {
    prefs.edit().putString("sort_method", sortMethod).apply()
}