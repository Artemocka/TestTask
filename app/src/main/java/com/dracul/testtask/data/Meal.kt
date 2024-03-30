package com.dracul.testtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strMeal:String,
    val strCategory:String,
    val strInstructions:String,
    val strMealThumb:String,
)
data class Meals(
    val meals:List<Meal>
)
