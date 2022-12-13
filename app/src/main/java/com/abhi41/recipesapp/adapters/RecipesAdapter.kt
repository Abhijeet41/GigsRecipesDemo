package com.abhi41.recipesapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhi41.recipesapp.data.dto.FoodRecipeDto
import com.abhi41.recipesapp.model.Result
import com.abhi41.recipesapp.utils.RecipesDiffUtils
import com.abhi41.rrecipesapp.databinding.RecipesRowLayoutBinding


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder> (){

    private  val TAG = "RecipesAdapter"
    private var recipes = emptyList<Result>()

    class MyViewHolder(
        private val binding: RecipesRowLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curretntRecipe = recipes[position]
        holder.bind(curretntRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipeDto) {
        val recipesDiffUtil = RecipesDiffUtils(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results.map { it.toResult() }
        diffUtilResult.dispatchUpdatesTo(this)
        Log.d(TAG, "setData: $recipes")
        // notifyDataSetChanged() we don't need this
    }
}