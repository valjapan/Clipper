package com.valjapan.clipper.viewmodels

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.valjapan.clipper.datas.History
import com.valjapan.clipper.datas.HistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    enum class Destination {
        COLLECTION,
        EDIT
    }

    val historyList: LiveData<List<History>>

    private val _move: MutableLiveData<Pair<Destination, Parcelable?>> = MutableLiveData()


    var id by Delegates.notNull<Int>()

    private val database = HistoryDatabase.getDatabase(application.applicationContext)

    init {
        historyList = runBlocking {
            database.historyDao().observeAll()
        }
    }

    fun delete() {
        id.let { id ->
            viewModelScope.launch(Dispatchers.IO) {
                val history = database.historyDao().getItem(id) ?: return@launch
                database.historyDao().deleteHistoryData(history)
                this.launch(Dispatchers.Main) {
                    _move.postValue(Pair(Destination.COLLECTION, null))
                }
            }
        }
    }

    fun fetchHistory(id: Int): LiveData<History?> =
        runBlocking(Dispatchers.IO) { database.historyDao().observe(id) }

}