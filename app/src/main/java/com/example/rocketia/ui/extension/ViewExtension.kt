package com.example.rocketia.ui.extension

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isGone
import androidx.core.view.isVisible

fun View.gone() {
    if (this.isVisible) this.visibility = View.GONE
}


fun View.visible() {
    if (this.isGone) this.visibility = View.VISIBLE
}

fun View.hideKeyboard() {
    val inputMethodManager = this.context.getSystemService(InputMethodManager::class.java)
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}