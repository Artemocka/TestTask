package com.dracul.testtask.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dracul.testtask.data.Meal

import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM Meal WHERE idMeal = :id")
    fun getById(id: Long): Meal?

    @Query("SELECT * FROM Meal ORDER BY idMeal")
    fun getAll(): Flow<List<Meal>>

    @Query("SELECT COUNT(*) FROM Meal")
    fun count(): Int

    @Query("DELETE FROM Meal")
    fun clear()

    @Insert
    fun insert(item: Meal): Long

    @Update
    fun update(item: Meal)

    @Delete
    fun delete(item: Meal)
}