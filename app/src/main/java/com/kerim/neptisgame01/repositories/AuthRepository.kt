package com.kerim.neptisgame01.repositories

import android.util.Log
import com.kerim.neptisgame01.models.ApiResponse
import com.kerim.neptisgame01.models.User
import com.kerim.neptisgame01.network.AuthService
import com.kerim.neptisgame01.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {
    private val authService: AuthService = ApiClient.createService(AuthService::class.java)

    fun register(user: User, callback: (User?) -> Unit) {
        authService.register(user).enqueue(object : Callback<ApiResponse<User>> {
            override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        Log.d("AuthRepository", "API Response: ${apiResponse.toString()}")
                        if (apiResponse.success) {
                            callback(apiResponse.data) // Access the data field of ApiResponse
                        } else {
                            Log.e("AuthRepository", "Registration failed: ${apiResponse.message}")
                            callback(null)
                        }
                    } else {
                        Log.e("AuthRepository", "Registration response body is null")
                        callback(null)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AuthRepository", "Registration failed: ${response.code()} - $errorBody")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                Log.e("AuthRepository", "Registration network error: ${t.message}")
                callback(null)
            }
        })
    }
}
