package com.dracul.testtask.api

import com.dracul.testtask.data.Categories
import com.dracul.testtask.data.Meals
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetrofitService {
    @GET("api/json/v1/1/search.php?s")
    suspend fun getMeals():Response<Meals>
    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories():Response<Categories>


    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://themealdb.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}