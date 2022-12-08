package com.thewind.space.main.ui.music.searchpage.vm

import com.thewind.space.main.ui.music.model.SongSrc
import com.thewind.space.main.ui.music.searchpage.services.searchMusic
import com.thewind.space.main.ui.music.ui.CommonMusicViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2022/12/9 上午2:38
 * @description:
 */
class SearchPageViewModel : CommonMusicViewModel() {

    fun search(keyword: String, num: Int = 30, page: Int = 1, src: SongSrc = SongSrc.MG) {
        MainScope().launch {
            withContext(Dispatchers.IO) {
                searchMusic(keyword, num, src)
            }.let {
                musicInfoListLiveData.postValue(it.toMutableList())
            }
        }
    }
}