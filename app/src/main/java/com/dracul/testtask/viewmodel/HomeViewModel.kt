package com.dracul.testtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dracul.testtask.data.Category
import com.dracul.testtask.data.Meal
import com.dracul.testtask.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel constructor(private val mainRepository: HomeRepository): ViewModel() {
    val errorMessage =MutableStateFlow<String>("")
    val mealList = MutableStateFlow<List<Meal>>(listOf())
    val categories = MutableStateFlow<List<Category>>(listOf())

    val loading = MutableStateFlow<Boolean>(false)

    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainRepository.getMeals()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    mealList.value = response.body()?.meals!!
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainRepository.getCategories()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    categories.value = response.body()?.categories!!
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }


}