package com.karbyshev.catstock.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

class ImageUtils(private val context: Context) {
    companion object {
        const val fileUriPrefix = "file://"
        var savedImagePath = ""
    }

    fun getRealPathFromURI(uri: Uri): String {
        val cursor: Cursor = context.getContentResolver().query(uri, null, null, null, null)
        cursor.moveToFirst()

        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }
}