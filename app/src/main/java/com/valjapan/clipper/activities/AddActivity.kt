package com.valjapan.clipper.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.valjapan.clipper.R
import com.valjapan.clipper.datas.History
import com.valjapan.clipper.datas.HistoryDatabase
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.*

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val doneButton: Button = findViewById(R.id.done_button)
        val editText: EditText = findViewById(R.id.editText)

        editText.background.clearColorFilter()

        doneButton.setOnClickListener {
            if (editText.text.isEmpty()) {
                Toast.makeText(applicationContext, "内容を入力してください", Toast.LENGTH_SHORT).show()
            } else {
                Completable.fromAction {
                    val database = HistoryDatabase.getDatabase(this)
                    val repository = (database.historyDao())
                    val history = History(0, Date(), editText.text.toString())
                    repository.insertHistoryData(history)
                }
                    .subscribeOn(Schedulers.io())
                    .doFinally { finish() }
                    .subscribe()
                Toast.makeText(applicationContext, "Clipperに保存しました", Toast.LENGTH_SHORT).show()
            }
        }
    }
}