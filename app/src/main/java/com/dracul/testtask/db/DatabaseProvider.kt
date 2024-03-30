package com.dracul.testtask.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dracul.testtask.data.Category
import com.dracul.testtask.data.Meal

@Database(
    entities = [
        Meal::class,
    Category::class
    ],
    version = DatabaseProvider.VERSION
)
abstract class DatabaseProvider : RoomDatabase() {
    abstract val mealDao: MealDao
    abstract val categoryDao: CategoryDao

    companion object {
        const val VERSION = 1
    }
}