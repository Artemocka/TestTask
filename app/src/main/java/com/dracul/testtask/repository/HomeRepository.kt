package com.dracul.testtask.repository

import com.dracul.testtask.api.RetrofitService

class HomeRepository(private val retrofitService: RetrofitService) {
    suspend fun  getMeals() = retrofitService.getMeals()
    suspend fun  getCategories() = retrofitService.getCategories()

}