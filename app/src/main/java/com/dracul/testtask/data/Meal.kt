package com.dracul.testtask.data

data class Meal(
    val idMeal: String,
    val strMeal:String,
    val strCategory:String,
    val strArea:String,
    val strInstructions:String,
    val strMealThumb:String,
)
data class Meals(
    val meals:List<Meal>
)
