package com.wedid.util

import com.wedid.api.ApiClient
import com.wedid.model.ErrorResponse
import okhttp3.ResponseBody

object NetworkUtil {
    fun getErrorResponse(errorBody: ResponseBody): ErrorResponse {
        return ApiClient.retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, ErrorResponse::class.java.annotations).convert(errorBody)!!
    }
}