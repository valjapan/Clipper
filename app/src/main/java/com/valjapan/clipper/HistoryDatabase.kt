package com.valjapan.clipper

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {

        fun getDatabase(context: Context): HistoryDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return DataModule.provideHistoryDB(context)
        }
    }
}
