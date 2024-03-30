package com.dracul.testtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dracul.testtask.data.Category
import com.dracul.testtask.data.FilterChip
import com.dracul.testtask.data.Meal
import com.dracul.testtask.data.toFilterChipList
import com.dracul.testtask.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val mainRepository: HomeRepository) : ViewModel() {
    var meals: MutableStateFlow<List<Meal>> = MutableStateFlow(listOf())

    private var categories: List<Category> = listOf()
    val filterChipList: MutableStateFlow<List<FilterChip>> = MutableStateFlow(categories.toFilterChipList())

    init {
        getMeals()
        getCategories()
    }


    val errorMessage = MutableStateFlow<String>("")
    val loading = MutableStateFlow<Boolean>(false)

    fun setSelectedFilterChip(filterChip: FilterChip) {
        filterChipList.value = filterChipList.value.map {
            when {
                (it.category == filterChip.category) -> it.copy(isChecked = true)
                (it.isChecked) -> it.copy(isChecked = false)
                else -> it
            }
        }
    }


    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainRepository.getMeals()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    meals.value = response.body()?.meals!!
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
                    categories = response.body()?.categories!!
                    loading.value = false
                    filterChipList.value = categories.toFilterChipList()
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

