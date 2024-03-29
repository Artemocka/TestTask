package com.dracul.testtask.data

data class Category(
    val idCategory:String,
    val strCategory:String,
    val strCategoryThumb:String,
    val strCategoryDescription:String,
)

data class Categories(
    val categories:List<Category>
)