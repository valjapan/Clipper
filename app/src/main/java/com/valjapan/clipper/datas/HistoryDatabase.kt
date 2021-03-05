package com.valjapan.clipper.datas

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {

        fun getDatabase(context: Context): HistoryDatabase {
            return DataModule.provideHistoryDB(context)
        }
    }
}
