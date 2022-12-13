package com.abhi41.recipesapp.di

import android.content.Context
import com.abhi41.recipesapp.data.network.FoodRecipesApi
import com.abhi41.recipesapp.data.network.RecipesRepositoryImpl
import com.abhi41.recipesapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkhttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRecipesRepository(
        api: FoodRecipesApi,
        @ApplicationContext context: Context
    ) = RecipesRepositoryImpl(
        foodRecipesApi = api,
    )


}