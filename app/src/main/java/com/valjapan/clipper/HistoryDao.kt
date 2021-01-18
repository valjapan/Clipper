package com.valjapan.clipper

import androidx.room.*

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistoryData(vararg history: History)

    @Update
    fun updateHistoryData(vararg history: History)

    @Delete
    fun deleteHistoryData(vararg history: History)

    @Query("SELECT * FROM history")
    fun getAll(): List<History>
}