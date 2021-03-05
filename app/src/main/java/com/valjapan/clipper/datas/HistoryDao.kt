package com.valjapan.clipper.datas

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun observeAll(): LiveData<List<History>>

    @Query("SELECT * FROM history WHERE id == :id")
    fun getItem(id: Int): History?

    @Query("SELECT * FROM history WHERE id == :id")
    fun observe(id: Int): LiveData<History?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistoryData(history: History)

    @Update
    fun updateHistoryData(history: History)

    @Delete
    fun deleteHistoryData(history: History)
}