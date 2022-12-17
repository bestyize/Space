package com.thewind.space.main.ui.music.searchpage.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.space.config.router.AppRouter
import com.thewind.space.databinding.FragmentMusicSearchBinding
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.searchpage.ui.searchbar.SearchBarViewListener
import com.thewind.space.main.ui.music.searchpage.vm.SearchPageViewModel
import com.thewind.space.main.ui.music.ui.CommonMusicAdapter
import com.thewind.spacecore.notify.ToastHelper
import com.thewind.spacecore.uiutil.ViewUtils


private const val TAG = "[App]MusicSearchFragment"

class MusicSearchFragment : Fragment() {

    private lateinit var binding: FragmentMusicSearchBinding

    private lateinit var searchVM: SearchPageViewModel
    private var musicInfoList: MutableList<MusicInfo> = mutableListOf()

    private var mLastSearchText: String = "热门"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicSearchBinding.inflate(inflater)
        searchVM = ViewModelProvider(this)[SearchPageViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.csvSearch.applyColorTheme(Color.BLACK, Color.WHITE)
        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)
        binding.rvSearchResult.adapter = CommonMusicAdapter(musicInfoList)
        binding.rvSearchResult.addOnScrollListener(object : OnScrollListener() {
            private var lastPos: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastPos == musicInfoList.size - 1) {
                        searchVM.search(mLastSearchText, true)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastPos =
                    (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
                        ?: 0
            }
        })
        searchVM.musicInfoListLiveData.observe(viewLifecycleOwner) {
            musicInfoList.clear()
            musicInfoList.addAll(it)
            binding.rvSearchResult.adapter?.notifyDataSetChanged()
            if (it.isEmpty()) {
                ToastHelper.toast("什么也没搜到，换个关键词？")
            } else {
                ToastHelper.toast("为您更新了${it.size}条内容")
            }

        }
        searchVM.musicInfoListLiveDataLoadMore.observe(viewLifecycleOwner) {
            musicInfoList.addAll(it)
            binding.rvSearchResult.adapter?.notifyDataSetChanged()
            if (it.isEmpty()) {
                ToastHelper.toast("已经滑到底了，没有更多内容")
            } else {
                ToastHelper.toast("为您更新了${it.size}条内容")
            }
        }
        binding.csvSearch.searchListener = object : SearchBarViewListener {
            override fun onSearchClick(text: String) {
                mLastSearchText = text
                searchVM.search(text)
            }

            override fun onInputClick() {

            }

            override fun onBackClick() {

            }

            override fun onTextChanged(text: String) {

            }
        }
        searchVM.updateRecommendMusic()


    }

    override fun onResume() {
        super.onResume()
        ViewUtils.enterFullScreenMode(activity, false)
    }


}