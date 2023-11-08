package com.nadji.myapp.common.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import com.nadji.myapp.R
import java.util.*


fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showAlert(title: String, msg: String) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(msg)
        setIcon(R.drawable.ic_warning)
        setCancelable(false)

        setPositiveButton("Ok") { dialogInterface, i ->
            dialogInterface?.dismiss()
        }
    }.show()
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}



