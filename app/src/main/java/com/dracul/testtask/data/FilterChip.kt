package com.dracul.testtask.data

data class FilterChip(val category: Category, val isChecked: Boolean=false)

fun List<FilterChip>.getSelected():FilterChip?{
    return this.find {
        it.isChecked
    }
}
