package com.abhi41.recipesapp.data.network


import com.abhi41.recipesapp.data.dto.FoodRecipeDto
import retrofit2.Response
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi,
) {
    suspend fun getRecepies(quries: Map<String, String>): Response<FoodRecipeDto> {
        return foodRecipesApi.getRecipies(quries)
    }
}