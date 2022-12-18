package com.thewind.space.main.ui.music.indexpage.recommend.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thewind.space.databinding.FragmentIndexRecommendBinding
import com.thewind.space.main.ui.music.indexpage.model.RecommendCard
import com.thewind.space.main.ui.music.indexpage.recommend.ui.adapter.RecommendCardAdapter
import com.thewind.space.main.ui.music.indexpage.recommend.vm.IndexRecommendFragmentViewModel
import com.thewind.spacecore.notify.ToastHelper

/**
 * A simple [Fragment] subclass.
 * Use the [MusicRecommendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusicRecommendFragment(private var tabName: String) : Fragment() {

    private lateinit var binding: FragmentIndexRecommendBinding

    private lateinit var recommendVM: IndexRecommendFragmentViewModel

    private var cardList: MutableList<RecommendCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommendVM = ViewModelProvider(this)[IndexRecommendFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndexRecommendBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRecommendFeed.layoutManager = GridLayoutManager(context, 2)
        binding.rvRecommendFeed.adapter = RecommendCardAdapter(cardList)
        recommendVM.recommendFeeds.observe(viewLifecycleOwner) {
            cardList.clear()
            cardList.addAll(it)
            binding.rvRecommendFeed.adapter?.notifyDataSetChanged()
            binding.srlRefresh.isRefreshing = false
            if (it.isEmpty()) {
                ToastHelper.toast("请检查网络是否通畅")
            } else {
                ToastHelper.toast("为您更新了${it.size}条内容")
            }
        }
        recommendVM.recommendFeedsMore.observe(viewLifecycleOwner) {
            cardList.addAll(it)
            binding.rvRecommendFeed.adapter?.notifyDataSetChanged()
            binding.srlRefresh.isRefreshing = false
            if (it.isEmpty()) {
                ToastHelper.toast("已经滑到底了，没有更多内容")
            } else {
                ToastHelper.toast("为您更新了${it.size}条内容")
            }
        }
        binding.srlRefresh.apply {
            setColorSchemeColors(Color.RED)
        }
        binding.srlRefresh.setOnRefreshListener {
            binding.srlRefresh.isRefreshing = true
            recommendVM.refresh(tabName)
        }
        binding.rvRecommendFeed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var lastPos: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (lastPos == cardList.size - 1) {
                        recommendVM.refresh(tabName, true)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastPos = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()?:0
            }
        })
        recommendVM.refresh(tabName)

    }

    companion object {

        @JvmStatic
        fun newInstance(tabName: String) = MusicRecommendFragment(tabName)
    }
}