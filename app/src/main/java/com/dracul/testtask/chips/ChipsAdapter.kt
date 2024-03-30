package com.dracul.testtask.chips

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dracul.testtask.R
import com.dracul.testtask.data.FilterChip
import com.dracul.testtask.databinding.ItemChipBinding


class ChipsAdapter(private val listener: OnChipListner) : ListAdapter<FilterChip, ChipsAdapter.ViewHolder>(ChipsDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).category.idCategory.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChipBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            if (viewHolder.adapterPosition !=-1) {
                val filterChip = currentList[viewHolder.adapterPosition]
                listener.onChipChecked(filterChip)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    class ViewHolder(private val binding: ItemChipBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterChip) {
            binding.run {
                chip.text = item.category.strCategory
                chip.isChecked = item.isChecked

                if (chip.isChecked){
                    chip.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.root.context,R.color.selectedChipBackground))
                    chip.setTextColor( ColorStateList.valueOf(ContextCompat.getColor(this.root.context,R.color.selectedChipBackground)))
                }else{
                        chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this.root.context,R.color.backgroundColor))
                    chip.setTextColor(ContextCompat.getColor(this.root.context,R.color.textColor))

                }
            }
        }

    }

    interface OnChipListner {
        fun onChipChecked(category: FilterChip)
    }

}