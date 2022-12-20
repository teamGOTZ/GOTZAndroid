package com.gotz.data.dao

import androidx.room.*
import com.gotz.data.db.GotzDataBase.Companion.DATE_TABLE
import com.gotz.data.model.DateEntity

@Dao
interface DateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDate(date: DateEntity)

    @Delete
    fun deleteDate(date: DateEntity)

    @Query("SELECT * FROM $DATE_TABLE ORDER BY dateId ASC")
    fun getDates(): List<DateEntity>

    @Query("DELETE FROM $DATE_TABLE")
    fun clearDates()
}