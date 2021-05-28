package com.example.movieappretrofitexample.data.db

import androidx.room.TypeConverter
import java.sql.Date

class DbTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimesStamp(date: Date?):Long?{
        return date?.time
    }


}