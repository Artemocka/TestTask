package com.dracul.testtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class Category(
    @PrimaryKey
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
)

data class Categories(
    val categories: List<Category>
)

fun List<Category>.toFilterChipList(): List<FilterChip> = map { FilterChip(it) }