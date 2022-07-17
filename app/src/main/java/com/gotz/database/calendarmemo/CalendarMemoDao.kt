package com.gotz.database.calendarmemo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarMemoDao {
    @Query("SELECT * FROM calendarmemo ORDER BY start ASC, isAllDay ASC ")
    fun getAll(): Flow<List<CalendarMemo>>

    @Query("SELECT * FROM calendarmemo WHERE uid = :uid ")
    fun getUidItem(uid:Int):Flow<CalendarMemo>

    @Query("UPDATE calendarmemo SET title = :title, content = :content, isAllDay = :isAllDay, start = :start, 'end' = :end WHERE uid = :uid ")
    suspend fun updateUidItem(title:String, content: String, isAllDay: Boolean, start: Long, end: Long, uid: Int)

    @Query("DELETE FROM calendarmemo WHERE uid = :uid")
    suspend fun deleteUidItem(uid: Int)

    /*
    @Query("SELECT * FROM calendarmemo WHERE start BETWEEN :startDay AND :endDay ORDER BY start ASC, isAllDay DESC")
    fun getDaySchedule(startDay: Long, endDay: Long): Flow<List<CalendarMemo>>
    */
    @Insert
    suspend fun insert(vararg calendarMemo: CalendarMemo)

    @Query("DELETE FROM calendarmemo")
    suspend fun deleteAll()
}