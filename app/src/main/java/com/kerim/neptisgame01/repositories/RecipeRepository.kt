package com.kerim.neptisgame01.repositories

import com.kerim.neptisgame01.models.Recipe
import com.kerim.neptisgame01.network.ApiClient
import com.kerim.neptisgame01.network.RecipeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeRepository {
    private val recipeService: RecipeService = ApiClient.createService(RecipeService::class.java)

    fun getAllRecipes(callback: (List<Recipe>?) -> Unit) {
        recipeService.getAllRecipes().enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                callback(null)
            }
        })
    }
}
