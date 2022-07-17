package com.gotz.util

import com.gotz.api.ApiClient
import com.gotz.model.ErrorResponse
import okhttp3.ResponseBody

object NetworkUtil {
    fun getErrorResponse(errorBody: ResponseBody): ErrorResponse {
        return ApiClient.retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, ErrorResponse::class.java.annotations).convert(errorBody)!!
    }
}