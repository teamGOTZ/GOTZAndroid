package com.gotz.data.mapper

import com.gotz.data.model.DateEntity
import com.gotz.domain.model.Date

object DateMapper {
    fun DateEntity.toMapper() = Date(
        dateId = dateId,
    )

    fun Date.toMapper() = DateEntity(
        dateId = dateId
    )
}