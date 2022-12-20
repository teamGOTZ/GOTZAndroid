package com.gotz.data.model

/**
 * T1H : 기온
 * RN1 : 1시간 강수량
 * UUU : 동서 바람 성분
 * VVV : 남북 바람 성분
 * REH : 습도
 * PTY : 강수 형태
 * VEC : 풍향
 * WSD : 풍속
 */

data class WeatherEntity(
    var response: Response? = Response()
)

data class Response(
    var header: Header? = Header(),
    var body: Body? = Body()
)

data class Header(
    var resultCode: String? = null,
    var resultMsg: String? = null
)

data class Body(
    var dataType: String? = null,
    var items: Items? = Items(),
    var pageNo: Int? = null,
    var numOfRows: Int? = null,
    var totalCount: Int? = null
)

data class Items(
    var item: ArrayList<Item> = arrayListOf()
)

data class Item(
    var baseDate: String? = null,
    var baseTime: String? = null,
    var category: String? = null,
    var nx: Int? = null,
    var ny: Int? = null,
    var obsrValue: String? = null
)