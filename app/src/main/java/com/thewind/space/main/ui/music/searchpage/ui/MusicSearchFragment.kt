package com.thewind.space.main.ui.music.searchpage.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thewind.space.databinding.FragmentMusicSearchBinding
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.searchpage.ui.searchbar.CommonSearchBarView
import com.thewind.space.main.ui.music.searchpage.ui.searchbar.SearchBarViewListener
import com.thewind.space.main.ui.music.searchpage.vm.SearchPageViewModel
import com.thewind.space.main.ui.music.ui.CommonMusicAdapter


private const val TAG = "[App]MusicSearchFragment"

class MusicSearchFragment : Fragment() {

    private lateinit var binding: FragmentMusicSearchBinding

    private var searchVM: SearchPageViewModel = SearchPageViewModel()
    private var musicInfoList: MutableList<MusicInfo> = mutableListOf()

    private lateinit var mSearchBar: CommonSearchBarView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)
        binding.rvSearchResult.adapter = CommonMusicAdapter(musicInfoList)
        searchVM.musicInfoListLiveData.observe(viewLifecycleOwner) {
            musicInfoList.clear()
            musicInfoList.addAll(it)
            binding.rvSearchResult.adapter?.notifyDataSetChanged()
        }
        mSearchBar = CommonSearchBarView(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        binding.root.addView(mSearchBar)
        mSearchBar.searchListener = object : SearchBarViewListener {
            override fun onSearchClick(text: String) {
                searchVM.search(text)
            }

            override fun onInputClick() {

            }

            override fun onBackClick() {

            }

            override fun onTextChanged(text: String) {

            }
        }


    }


}