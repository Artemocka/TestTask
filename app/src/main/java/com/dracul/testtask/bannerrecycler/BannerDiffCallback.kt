package com.dracul.testtask.bannerrecycler

import androidx.recyclerview.widget.DiffUtil
import com.dracul.testtask.data.Banner

class BannerDiffCallback: DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

}