package com.example.wgpgkt.model

data class LoginModel(
    val code: Int,
    val message: String,
    val response: Response
){
    data class Response(
        val id: Int
    )
}

