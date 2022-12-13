package com.abhi41.recipesapp.data.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class FoodRecipeDto(
    @SerializedName("results")
    val results: List<ResultDto>
)
