package com.abhi41.recipesapp.data.network


import com.abhi41.recipesapp.data.dto.FoodRecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipies(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipeDto>


}