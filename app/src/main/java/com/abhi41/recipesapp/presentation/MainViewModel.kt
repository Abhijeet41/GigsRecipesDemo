package com.abhi41.recipesapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.recipesapp.data.dto.FoodRecipeDto
import com.abhi41.recipesapp.data.network.RecipesRepositoryImpl
import com.abhi41.recipesapp.utils.Constants
import com.abhi41.recipesapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RecipesRepositoryImpl

) : ViewModel(){
    var recipiesResponse: MutableLiveData<NetworkResult<FoodRecipeDto>> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRecipes(applyQueries())
        }
    }

    fun getRecipes(quries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(quries)
    }

    private suspend fun getRecipesSafeCall(quries: Map<String, String>) {
        try {
            /* online getting food recipes */
            val response = repository.getRecepies(quries = quries)
            recipiesResponse.value = handleFoodRecipesResponse(response)

        } catch (e: Exception) {
            recipiesResponse.value = NetworkResult.Error("Recipes not found")
        }
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipeDto>): NetworkResult<FoodRecipeDto>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Connection Timeout!")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("Api Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }


    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = Constants.DEFAULT_MEAL_TYPE
        queries[Constants.QUERY_DIET] = Constants.DEFAULT_DIET_TYPE
        queries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}