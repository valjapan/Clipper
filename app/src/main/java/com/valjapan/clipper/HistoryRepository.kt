package com.valjapan.clipper

import android.content.Context
import android.util.Log
import android.widget.Toast

class HistoryRepository(private val historyDao: HistoryDao) {

    fun getHistoryList(): List<History> {
        return try {
            historyDao.getAll()
        } catch (cause: Throwable) {
            Log.e("HistoryRepository", cause.toString())
            emptyList<History>()
        }
    }

    suspend fun addHistoryTask(context: Context, history: History) {
        try {
            historyDao.insertHistoryData(history)
        } catch (cause: Throwable) {
            Log.e("HistoryRepository", cause.toString())
            Toast.makeText(context, "エラーにより追加できませんでした : " + cause.toString(), Toast.LENGTH_LONG)
                .show()
        }
    }

    suspend fun deleteHistoryTask(context: Context, history: History) {
        try {
            historyDao.deleteHistoryData(history)
        } catch (cause: Throwable) {
            Log.e("HistoryRepository", cause.toString())
            Toast.makeText(context, "エラーにより削除できませんでした : " + cause.toString(), Toast.LENGTH_LONG)
                .show()
        }
    }
}