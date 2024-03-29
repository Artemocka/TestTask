package com.dracul.testtask.recycler

import androidx.recyclerview.widget.DiffUtil
import com.dracul.testtask.data.Meal

class MealDiffCallback: DiffUtil.ItemCallback<Meal>() {
    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem == newItem
    }

}