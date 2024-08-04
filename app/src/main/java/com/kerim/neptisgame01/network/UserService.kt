package com.kerim.neptisgame01.network

import com.kerim.neptisgame01.models.User
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("/users")
    fun getAllUsers(): Call<List<User>>
}
