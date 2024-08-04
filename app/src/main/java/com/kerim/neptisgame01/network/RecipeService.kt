package com.kerim.neptisgame01.network

import com.kerim.neptisgame01.models.Recipe
import retrofit2.Call
import retrofit2.http.GET

interface RecipeService {
    @GET("/recipes/all")
    fun getAllRecipes(): Call<List<Recipe>>
}
