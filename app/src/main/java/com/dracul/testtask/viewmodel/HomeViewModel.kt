package com.dracul.testtask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dracul.testtask.data.Banner
import com.dracul.testtask.data.FilterChip
import com.dracul.testtask.data.Meal
import com.dracul.testtask.data.toFilterChipList
import com.dracul.testtask.repository.HomeRepository
import com.dracul.testtask.db.DatabaseProviderWrap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class HomeViewModel(private val mainRepository: HomeRepository) : ViewModel() {
    var meals: MutableStateFlow<List<Meal>> = MutableStateFlow(listOf())
    val filterChipList: MutableStateFlow<List<FilterChip>> = MutableStateFlow(listOf())
    val banners = listOf(Banner("banner_1"), Banner("banner_2"))
    val errorMessage = MutableStateFlow<String>("")
    val loading = MutableStateFlow<Boolean>(false)

    init {
        getMeals()
        getCategories()

    }


    fun setSelectedFilterChip(filterChip: FilterChip) {
        filterChipList.value = filterChipList.value.map {
            when {
                (it.category == filterChip.category) -> it.copy(isChecked = !it.isChecked)
                (it.isChecked) -> it.copy(isChecked = false)
                else -> it
            }
        }
    }


    fun getMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.value = true

            val response = try {
                mainRepository.getMeals()

            } catch (e: UnknownHostException) {
                onError("No internet connections!")
                null
            } catch (e: SocketTimeoutException) {
                onError("Bad internet connections")
                null
            } catch (e: Exception) {
                onError("Unknown error")
                null
            }
            if (response?.isSuccessful == true) {
                meals.value = response.body()?.meals!!
                loading.value = false
                response.body()?.meals?.forEach {
                    if (DatabaseProviderWrap.MealDao.getById(it.idMeal.toLong())==null){
                        DatabaseProviderWrap.MealDao.insert(it)
                    }
                }
            } else {
                poop("meal db")
                DatabaseProviderWrap.MealDao.getAll().collect {
                    meals.value = it
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                mainRepository.getCategories()

            } catch (e: UnknownHostException) {
                onError("No internet connections!")
                null
            } catch (e: SocketTimeoutException) {
                onError("Bad internet connections")
                null
            } catch (e: Exception) {
                onError("Unknown error")
                null
            }
            if (response?.isSuccessful == true) {
                filterChipList.value = response.body()?.categories!!.toFilterChipList()
                loading.value = false
                response.body()?.categories?.forEach {
                    if (DatabaseProviderWrap.categoryDao.getById(it.idCategory.toLong())==null){
                        DatabaseProviderWrap.categoryDao.insert(it)
                    }
                }
            } else {
                poop("category db")
                DatabaseProviderWrap.categoryDao.getAll().collect {
                    filterChipList.value = it.toFilterChipList()
                }
            }

        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}

fun Any.poop(s: String) {
    Log.e("testtask", "[${javaClass.simpleName}] $s")
}