package com.dracul.testtask.chips

import androidx.recyclerview.widget.DiffUtil
import com.dracul.testtask.data.FilterChip

class ChipsDiffCallback: DiffUtil.ItemCallback<FilterChip>() {
    override fun areItemsTheSame(oldItem: FilterChip, newItem: FilterChip): Boolean {
        return oldItem.category.idCategory == newItem.category.idCategory
    }

    override fun areContentsTheSame(oldItem: FilterChip, newItem: FilterChip): Boolean {
        return oldItem == newItem
    }

}