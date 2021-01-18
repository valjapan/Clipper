package com.valjapan.clipper

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(History::class), version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}