package com.valjapan.clipper.datas

import androidx.room.TypeConverter
import java.util.*

class DateConverters {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? = value?.let {
        Date(it)
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? = date?.time
}