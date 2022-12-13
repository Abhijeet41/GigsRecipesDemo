package com.abhi41.recipesapp.data.dto

import com.abhi41.recipesapp.model.Result
import com.google.gson.annotations.SerializedName

data class ResultDto(

    @SerializedName("title")
    val title: String,

    @SerializedName("summary")
    val summary: String,

    @SerializedName("image")
    val sourceUrl: String,

    @SerializedName("vegan")
    val vegan: Boolean,

    @SerializedName("vegetarian")
    val vegetarian: Boolean,

    @SerializedName("veryHealthy")
    val veryHealthy: Boolean

) {
    fun toResult(): Result {
        return Result(
            title, summary, sourceUrl
        )
    }
}
