package com.valjapan.clipper.datas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "history_date") val historyDate: Date,
    @ColumnInfo(name = "history_text") var historyText: String?,
)