package com.thewind.space.main.ui.music.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thewind.space.main.ui.music.model.MusicInfo

/**
 * @author: read
 * @date: 2022/12/9 上午2:35
 * @description:
 */
open class CommonMusicViewModel: ViewModel() {
    val musicInfoListLiveData = MutableLiveData<MutableList<MusicInfo>>()
    val musicInfoListLiveDataLoadMore = MutableLiveData<MutableList<MusicInfo>>()
}