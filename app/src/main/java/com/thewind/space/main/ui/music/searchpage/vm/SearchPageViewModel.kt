package com.thewind.space.main.ui.music.searchpage.vm

import androidx.lifecycle.viewModelScope
import com.thewind.space.main.ui.music.model.SongSrc
import com.thewind.space.main.ui.music.searchpage.services.searchMusic
import com.thewind.space.main.ui.music.ui.CommonMusicViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2022/12/9 上午2:38
 * @description:
 */
class SearchPageViewModel : CommonMusicViewModel() {

    var requestCount = 0

    fun search(keyword: String, loadMore: Boolean = false) {
        val page = requestCount
        if (loadMore) {
            requestCount++
        } else {
            requestCount = 0
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchMusic(keyword, 30, page, SongSrc.MG)
            }.let {
                if (loadMore) musicInfoListLiveDataLoadMore.postValue(it.toMutableList()) else musicInfoListLiveData.postValue(
                    it.toMutableList()
                )
            }
        }
    }

    fun updateRecommendMusic() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchMusic("热门", 30, 0, SongSrc.QQ)
            }.let {
                musicInfoListLiveData.postValue(it.toMutableList())
            }
        }
    }
}