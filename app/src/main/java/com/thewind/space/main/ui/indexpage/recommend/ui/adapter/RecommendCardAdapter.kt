package com.thewind.space.main.ui.indexpage.recommend.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter
import com.thewind.space.databinding.RecommendCardItemAdapterBinding
import com.thewind.space.main.ui.indexpage.model.RecommendCard
import com.thewind.space.main.ui.indexpage.model.RecommendCardType
import com.thewind.spacecore.notify.ToastHelper

/**
 * @author: read
 * @date: 2022/12/17 下午12:32
 * @description:
 */

class RecommendCardAdapter(val list: List<RecommendCard>) :
    RecyclerView.Adapter<RecommendCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recommend_card_item_adapter, parent, false)
        val binding = RecommendCardItemAdapterBinding.bind(view)
        return RecommendCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendCardViewHolder, position: Int) {
        val item = list[position]
        holder.binding.tvMainTitle.text = item.title
        holder.binding.tvSubTitle.text = item.subTitle
        Glide.with(holder.binding.root.context).load(item.coverUrl)
            .placeholder(R.drawable.recommend_place_holder).into(holder.binding.ivCover)
        holder.binding.root.setOnClickListener {
           CardActionHandler.doAction(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class RecommendCardViewHolder(val binding: RecommendCardItemAdapterBinding) :
    RecyclerView.ViewHolder(binding.root)