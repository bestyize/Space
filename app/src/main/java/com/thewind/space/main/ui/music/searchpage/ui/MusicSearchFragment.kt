package com.thewind.space.main.ui.music.searchpage.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.thewind.space.databinding.FragmentMusicSearchBinding
import com.thewind.space.main.ui.music.detailpage.ui.MusicPlayActivity
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.searchpage.ui.searchbar.SearchBarViewListener
import com.thewind.space.main.ui.music.searchpage.vm.SearchPageViewModel
import com.thewind.space.main.ui.music.ui.CommonMusicAdapter
import com.thewind.spacecore.uiutil.ViewUtils


private const val TAG = "[App]MusicSearchFragment"

class MusicSearchFragment : Fragment() {

    private lateinit var binding: FragmentMusicSearchBinding

    private var searchVM: SearchPageViewModel = SearchPageViewModel()
    private var musicInfoList: MutableList<MusicInfo> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.csvSearch.applyColorTheme(Color.RED, Color.WHITE)
        binding.rvSearchResult.layoutManager = LinearLayoutManager(context)
        binding.rvSearchResult.adapter = CommonMusicAdapter(musicInfoList).apply {
            selectListener = object : CommonMusicAdapter.OnItemSelectListener {
                override fun onClicked(musicInfo: MusicInfo) {
                    val intent = Intent(context, MusicPlayActivity::class.java)
                    intent.putExtra("music_info", musicInfo)
                    startActivity(intent)
                }

            }
        }
        searchVM.musicInfoListLiveData.observe(viewLifecycleOwner) {
            musicInfoList.clear()
            musicInfoList.addAll(it)
            binding.rvSearchResult.adapter?.notifyDataSetChanged()
        }
        binding.csvSearch.searchListener = object : SearchBarViewListener {
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

    override fun onResume() {
        super.onResume()
        ViewUtils.enterFullScreenMode(activity, false)
    }


}