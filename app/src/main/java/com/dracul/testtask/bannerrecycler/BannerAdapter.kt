package com.dracul.testtask.bannerrecycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dracul.testtask.R
import com.dracul.testtask.data.Banner
import com.dracul.testtask.databinding.ItemBannerBinding


class BannerAdapter : ListAdapter<Banner, BannerAdapter.ViewHolder>(BannerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context),
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
        private val binding: ItemBannerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Banner) {
            binding.run {

                when(item.banner){
                    "banner_1"-> banner.setImageResource(R.drawable.banner_1)
                    "banner_2"-> banner.setImageResource(R.drawable.banner_2)
                }
            }
        }
    }
}

