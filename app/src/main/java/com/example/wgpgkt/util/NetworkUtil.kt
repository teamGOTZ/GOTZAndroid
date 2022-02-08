package com.example.wgpgkt.util

import com.example.wgpgkt.api.ApiClient
import com.example.wgpgkt.model.ErrorResponse
import okhttp3.ResponseBody

object NetworkUtil {
    fun getErrorResponse(errorBody: ResponseBody): ErrorResponse {
        return ApiClient.retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, ErrorResponse::class.java.annotations).convert(errorBody)!!
    }
}