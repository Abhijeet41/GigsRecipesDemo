package com.abhi41.recipesapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.abhi41.recipesapp.data.dto.FoodRecipeDto
import com.abhi41.recipesapp.utils.NetworkResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<FoodRecipeDto>?
        ) {
            when (view) {
                is ImageView -> {
                    view.isVisible = apiResponse is NetworkResult.Error
                }
                is TextView -> {
                    view.isVisible = apiResponse is NetworkResult.Error
                    view.text = apiResponse?.message.toString()
                }
            }
        }

    }

}