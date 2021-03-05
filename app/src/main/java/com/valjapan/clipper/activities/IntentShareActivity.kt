package com.valjapan.clipper.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.valjapan.clipper.datas.History
import com.valjapan.clipper.datas.HistoryDatabase
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.*

@AndroidEntryPoint
class IntentShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //RxJavaを使ってメインスレッドでのDB書き込みを回避する
            Completable.fromAction {
                saveDatabase(intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString())
            }
                .subscribeOn(Schedulers.io())
                .subscribe()
            Toast.makeText(applicationContext, "Clipperに保存しました", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "APIレベル23以上が必要です", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun saveDatabase(text: String?) {
        val database = HistoryDatabase.getDatabase(this)
        val repository = (database.historyDao())
        val history = History(0, Date(), text)
        repository.insertHistoryData(history)
    }
}