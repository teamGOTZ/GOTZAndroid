package com.example.wgpgkt.model

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    val code: Int,
    val message: String,
    val response: Response
){
    data class Response(
        val id: Int
    )
}
