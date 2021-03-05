package com.valjapan.clipper.datas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "history_date") val historyDate: String?,
    @ColumnInfo(name = "history_text") val historyText: String?
)