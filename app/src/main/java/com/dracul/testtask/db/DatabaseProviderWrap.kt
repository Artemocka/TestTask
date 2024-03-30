package com.dracul.testtask.db

import android.content.Context
import androidx.room.Room

object DatabaseProviderWrap {

    const val VERSION = DatabaseProvider.VERSION
    private lateinit var provider: DatabaseProvider


    val MealDao: MealDao get() = provider.mealDao
    val categoryDao: CategoryDao get() = provider.categoryDao

    fun closeDao() = provider.close()


    fun createDao(context: Context) {
        provider = Room.databaseBuilder(context, DatabaseProvider::class.java, "app_db")
            .allowMainThreadQueries()
            .build()
    }
}