package com.gotz.model

data class RegisterBody(
    val dateOfBirth: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val username: String
)