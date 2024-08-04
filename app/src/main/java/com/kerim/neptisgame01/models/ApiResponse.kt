package com.kerim.neptisgame01.models

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)
