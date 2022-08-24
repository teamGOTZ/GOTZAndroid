package com.gotz.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gotz.cache.dao.CalendarDao
import com.gotz.cache.model.CacheCalendarEntity

@Database(
    version = 2,
    exportSchema = false,
    entities = [CacheCalendarEntity::class]
)
abstract class GotzDataBase: RoomDatabase() {

    companion object{
        const val CALENDAR_TABLE: String = "calendar"
    }

    abstract fun calendarDao(): CalendarDao
}