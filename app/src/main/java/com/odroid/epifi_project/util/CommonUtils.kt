package com.odroid.epifi_project.util

import android.content.Context
import android.widget.Toast

object CommonUtils {

    fun showToast(context: Context, toastMessage: String) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
    }
}