package com.valjapan.clipper

import android.widget.Toast
import javax.inject.Inject

class CopyObserver @Inject constructor(
    private val historyDatabase: HistoryDatabase
) {
    var isCopied = false

    fun copy() {
        isCopied = true

    }
}