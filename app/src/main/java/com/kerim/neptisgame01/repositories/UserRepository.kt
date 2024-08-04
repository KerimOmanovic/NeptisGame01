package com.kerim.neptisgame01.repositories

import com.kerim.neptisgame01.models.User
import com.kerim.neptisgame01.network.ApiClient
import com.kerim.neptisgame01.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val userService: UserService = ApiClient.createService(UserService::class.java)

    fun getAllUsers(callback: (List<User>?) -> Unit) {
        userService.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(null)
            }
        })
    }
}
