package com.gotz.model

data class ErrorResponse(
    val code: Int,
    val message: String,
    val response: Response
){
    data class Response(
        val cause: String,
        val details: String,
        val timestamp: String
    )
}

