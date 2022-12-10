package com.thewind.space.main.ui.music.searchpage.services

import android.util.Log
import com.thewind.space.api.base.RetrofitDefault
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.SongSrc

/**
 * @author: read
 * @date: 2022/12/9 上午2:54
 * @description:
 */

private const val TAG = "[App]MusicSearchHelper"

fun searchMusic(keyword: String, num: Int, src: SongSrc): List<MusicInfo> {
    try {
        return RetrofitDefault.create(MusicSearchService::class.java).searchMusic(keyword, num, src.src).execute().body()
            ?: emptyList()
    } catch (e: java.lang.Exception) {
        Log.i(TAG, "searchMusic, exception = $e")
    }
    return emptyList()
}