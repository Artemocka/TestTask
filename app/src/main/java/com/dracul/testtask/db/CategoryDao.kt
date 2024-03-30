package com.dracul.testtask.db;

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dracul.testtask.data.Category
import com.dracul.testtask.data.Meal

import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM Category WHERE idCategory = :id")
    fun getById(id: Long): Category?

    @Query("SELECT * FROM Category ORDER BY idCategory")
    fun getAll(): Flow<List<Category>>

    @Query("SELECT COUNT(*) FROM Category")
    fun count(): Int

    @Query("DELETE FROM Category")
    fun clear()

    @Insert
    fun insert(item: Category): Long

    @Update
    fun update(item: Category)

    @Delete
    fun delete(item: Category)
}