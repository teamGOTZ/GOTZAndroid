package com.gotz.di

import android.content.Context
import androidx.datastore.rxjava2.rxDataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gotz.cache.database.GotzDataBase
import com.gotz.cache.datastore.UserDataStoreSerializer
import com.gotz.cache.repository.user.CacheUserDataSourceImpl
import com.gotz.data.source.calendar.CacheCalendarDataSource
import com.gotz.data.source.user.CacheUserDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME: String = "gotz.db"
val Context.rxUserDataStore by rxDataStore(fileName = "user.proto", serializer = UserDataStoreSerializer)

@ExperimentalCoroutinesApi
val cacheLayerModule = module{

    single{
        Room.databaseBuilder(androidApplication(), GotzDataBase::class.java, DATABASE_NAME)
            .addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // DB 초기화
                }
            }).build()
    }

    single{ get<GotzDataBase>().calendarDao() }

    factory<CacheUserDataSource> { CacheUserDataSourceImpl(androidContext().rxUserDataStore) }
    factory<CacheCalendarDataSource> { get() }
}