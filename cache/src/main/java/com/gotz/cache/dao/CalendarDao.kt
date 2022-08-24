package com.gotz.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gotz.cache.model.CacheCalendarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarDao {
    @Insert
    suspend fun createCalendar(vararg entity: CacheCalendarEntity)

    @Query("SELECT * FROM calendar ORDER BY start ASC, isAllDay ASC ")
    fun readAllCalendar(): Flow<List<CacheCalendarEntity>>

    @Query("SELECT * FROM calendar WHERE uid = :uid ")
    fun readCalendarByUid(uid:Int): Flow<CacheCalendarEntity>

    @Query("UPDATE calendar SET title = :title, content = :content, isAllDay = :isAllDay, start = :start, 'end' = :end WHERE uid = :uid ")
    suspend fun updateCalendarByUid(title:String, content: String, isAllDay: Boolean, start: Long, end: Long, uid: Int)

    @Query("DELETE FROM calendar WHERE uid = :uid")
    suspend fun deleteCalendarByUid(uid: Int)

    @Query("DELETE FROM calendar")
    suspend fun deleteAllCalendar()
}