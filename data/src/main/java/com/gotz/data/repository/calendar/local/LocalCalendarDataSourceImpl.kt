package com.gotz.data.repository.calendar.local

class LocalCalendarDataSourceImpl {
}

//class CacheCalendarDataSourceImpl(
//    private val calendarDao: CalendarDao
//): CacheCalendarDataSource{
//    override suspend fun createCalendar(entity: CalendarEntity) {
//        calendarDao.createCalendar(entity.toMapper())
//    }
//
//    override fun readAllCalendar(): Flow<List<CalendarEntity>> =
//        calendarDao.readAllCalendar().map { list ->
//            val mutableList = mutableListOf<CalendarEntity>()
//            list.map { cacheCalendarEntity ->
//                mutableList.add(cacheCalendarEntity.toMapper())
//            }
//            mutableList
//        }
//
//    override fun readCalendarByUid(uid: Int): Flow<CalendarEntity> =
//        calendarDao.readCalendarByUid(uid).map { cacheCalendarEntity ->
//            cacheCalendarEntity.toMapper()
//        }
//
//    override suspend fun updateCalendar(entity: CalendarEntity) {
//        calendarDao.updateCalendarByUid(entity.title, entity.content, entity.isAllDay, entity.start, entity.end, entity.uid)
//    }
//
//    override suspend fun deleteCalendar(uid: Int) {
//        calendarDao.deleteCalendarByUid(uid)
//    }
//}