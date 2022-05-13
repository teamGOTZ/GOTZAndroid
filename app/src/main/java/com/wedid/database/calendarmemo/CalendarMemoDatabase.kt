package com.wedid.database.calendarmemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [CalendarMemo::class], version = 1)
abstract class CalendarMemoDatabase: RoomDatabase() {
    abstract fun calendarMemoDao(): CalendarMemoDao

    private class CalendarMemoDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // DB Init
        }
    }

    companion object{
        private var instance: CalendarMemoDatabase? = null

        @Synchronized
        fun getInstance(context: Context, scope: CoroutineScope): CalendarMemoDatabase?{
            if(instance == null){
                synchronized(CalendarMemoDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext, CalendarMemoDatabase::class.java, "calendar-memo").build()
                }
            }
            return instance
        }
    }

}