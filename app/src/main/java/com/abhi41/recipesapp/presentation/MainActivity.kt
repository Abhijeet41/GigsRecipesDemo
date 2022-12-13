package com.abhi41.recipesapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhi41.recipesapp.adapters.RecipesAdapter
import com.abhi41.recipesapp.model.Result
import com.abhi41.recipesapp.utils.NetworkResult
import com.abhi41.rrecipesapp.R
import com.abhi41.rrecipesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val mAdapter by lazy { RecipesAdapter() }
    val recipes = emptyList<Result>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        observers()
        setupRecyclerview()
    }

    private fun setupRecyclerview() {
        binding.rvRecipes.adapter = mAdapter
        binding.rvRecipes.layoutManager = LinearLayoutManager(applicationContext)
        showShimmerEffect()
    }

    private fun observers() {
        mainViewModel.recipiesResponse.observe(this) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { foodRecipe ->
                        mAdapter.setData(foodRecipe)
                        hideShimmerEffect()
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        applicationContext,
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        }
    }

    private fun showShimmerEffect() {
        binding.rvRecipesShimmer.startShimmer()
        binding.rvRecipes.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.rvRecipesShimmer.stopShimmer()
        binding.rvRecipesShimmer.visibility = View.GONE
        binding.rvRecipes.visibility = View.VISIBLE
    }
}