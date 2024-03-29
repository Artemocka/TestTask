package com.dracul.testtask.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dracul.testtask.R
import com.dracul.testtask.data.Meal
import com.dracul.testtask.databinding.ItemMealBinding


class MealAdapter : ListAdapter<Meal, MealAdapter.ViewHolder>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }


    class ViewHolder(
        private val binding: ItemMealBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Meal) {
            binding.run {

                binding.tvName.text = item.strMeal
                binding.tvInstructions.text = item.strInstructions
                Glide
                    .with(binding.root)
                    .load(item.strMealThumb)
                    .centerCrop()
                    .into(ivIcon)
            }
        }
    }
}

