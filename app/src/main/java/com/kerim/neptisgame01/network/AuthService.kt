package com.kerim.neptisgame01.network

import com.kerim.neptisgame01.models.ApiResponse
import com.kerim.neptisgame01.models.AuthResponse
import com.kerim.neptisgame01.models.User
import com.kerim.neptisgame01.models.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<ApiResponse<AuthResponse>>

    @POST("/users/add")
    fun register(@Body user: User): Call<ApiResponse<User>>
}
