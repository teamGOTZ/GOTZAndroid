package com.gotz.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gotz.data.dao.DateDao
import com.gotz.data.dao.ScheduleDao
import com.gotz.data.dao.ScheduleWithDateDao
import com.gotz.data.model.CalendarEntity
import com.gotz.data.model.DateEntity
import com.gotz.data.model.ScheduleEntity
import com.gotz.data.model.ScheduleWithDateEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [DateEntity::class,
                ScheduleEntity::class]
)
abstract class GotzDataBase: RoomDatabase() {

    companion object{
        const val CALENDAR_TABLE: String = "calendar"
        const val DATE_TABLE: String = "date"
        const val SCHEDULE_TABLE: String = "schedule"
    }

    //abstract fun calendarDao(): CalendarDao
    abstract fun dateDao(): DateDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun scheduleWithDateDao(): ScheduleWithDateDao
}