package com.thewind.space.main.ui.music.searchpage.services

import com.thewind.space.api.base.RetrofitDefault
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.SongSrc

/**
 * @author: read
 * @date: 2022/12/9 上午2:54
 * @description:
 */

fun searchMusic(keyword: String, num: Int, src: SongSrc): List<MusicInfo> {
    return RetrofitDefault.create(MusicSearchService::class.java).searchMusic(keyword, num, src.name).execute().body()
        ?: emptyList()
}