package com.dracul.testtask.viewmodel

import android.util.Log
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
import retrofit2.Response
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
                categories = response.body()?.categories!!
                loading.value = false
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