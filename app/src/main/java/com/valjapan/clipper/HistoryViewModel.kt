package com.valjapan.clipper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class HistoryViewModel @AssistedInject constructor(
    private val copyObserver: CopyObserver,
    @Assisted private val copyId: String
) : ViewModel() {
    fun copy() {
        copyObserver.copy()
    }

    fun isCopied(): Boolean {
        return copyObserver.isCopied
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(copyId: String): HistoryViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            copyId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(copyId) as T
            }
        }
    }
}