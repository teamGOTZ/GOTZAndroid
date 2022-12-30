package com.gotz.data.mapper

import com.gotz.data.model.WeatherEntity
import com.gotz.domain.model.Weather

object WeatherMapper {

    fun WeatherEntity.toModel(): Weather {
        if(this.response?.header?.resultCode == "03") return Weather(skyStatus = -1, temperature = 0.0F)
        if(this.response?.body?.items?.item!!.isEmpty()) return Weather(skyStatus = -1, temperature = 0.0F)

        return Weather(
            temperature = this.response?.body?.items?.item!!.filter{
                it.category == "T1H"
            }[0].obsrValue!!.toFloat(),
            skyStatus = this.response?.body?.items?.item!!.filter{
                it.category == "PTY"
            }[0].obsrValue!!.toInt()
        )
    }
}