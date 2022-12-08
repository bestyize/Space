package com.thewind.space.main.ui.music.searchpage.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import com.thewind.space.R
import com.thewind.space.databinding.FragmentMusicSearchBinding
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.searchpage.vm.SearchPageViewModel
import com.thewind.space.main.ui.music.ui.CommonMusicAdapter
import java.net.URLEncoder



private const val TAG = "[App]MusicSearchFragment"

class MusicSearchFragment : Fragment() {

    private lateinit var binding: FragmentMusicSearchBinding

    private var searchVM: SearchPageViewModel = SearchPageViewModel()
    private var musicInfoList: MutableList<MusicInfo> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_music_search, container, false)
        binding = FragmentMusicSearchBinding.bind(view)
        return view
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
        binding.svSearch.setIconifiedByDefault(false)
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i(TAG, "onQueryTextChange, newText = $newText")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val keyword = URLEncoder.encode(it)
                    searchVM.search(keyword)
                }
                return true

            }
        })


    }


}