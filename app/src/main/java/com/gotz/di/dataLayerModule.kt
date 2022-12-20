package com.gotz.di

import android.content.Context
import androidx.datastore.dataStore
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gotz.data.api.RetrofitCreator
import com.gotz.data.api.RetrofitProvider
import com.gotz.data.api.WeatherAPI
import com.gotz.data.db.GotzDataBase
import com.gotz.data.datastore.UserDataStoreSerializer
import com.gotz.data.repository.user.UserRepositoryImpl
import com.gotz.data.repository.user.local.LocalUserDataSourceImpl
import com.gotz.data.repository.calendar.local.CacheCalendarDataSource
import com.gotz.data.repository.schedule.ScheduleRepositoryImpl
import com.gotz.data.repository.schedule.local.LocalScheduleDataSource
import com.gotz.data.repository.schedule.local.LocalScheduleDataSourceImpl
import com.gotz.data.repository.user.local.LocalUserDataSource
import com.gotz.data.repository.weather.WeatherRepositoryImpl
import com.gotz.data.repository.weather.remote.RemoteWeatherDataSource
import com.gotz.data.repository.weather.remote.RemoteWeatherDataSourceImpl
import com.gotz.domain.repository.schedule.ScheduleRepository
import com.gotz.domain.repository.user.UserRepository
import com.gotz.domain.repository.weather.WeatherRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME: String = "gotz.db"
val Context.userDataStore by dataStore(fileName = "user.proto", serializer = UserDataStoreSerializer)

val dataLayerModule = module {

    /**
     * Retrofit
     */
    single { RetrofitCreator.instance }
    single { RetrofitCreator.instance.weatherAPI() }

    /**
     * DB
     */
    single{
        Room.databaseBuilder(androidApplication(), GotzDataBase::class.java, DATABASE_NAME)
            .addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // DB 초기화
                }
            }).build()
    }

    /**
     * DAO
     */
    single{ get<GotzDataBase>().dateDao() }
    single{ get<GotzDataBase>().scheduleDao() }
    single{ get<GotzDataBase>().scheduleWithDateDao() }

    /**
     * DataSource
     */
    factory<LocalUserDataSource> { LocalUserDataSourceImpl(androidContext().userDataStore) }
    factory<LocalScheduleDataSource> { LocalScheduleDataSourceImpl(get(), get(), get()) }
    factory<RemoteWeatherDataSource> { RemoteWeatherDataSourceImpl(get()) }

    /**
     * Repository
     */
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<ScheduleRepository> { ScheduleRepositoryImpl(get())}
    factory<WeatherRepository> { WeatherRepositoryImpl(get()) }
}