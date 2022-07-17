package com.gotz.model

data class RegisterModel(
    val code: Int,
    val message: String,
    val response: Response
){
    data class Response(
        val id: Int
    )
}
