package com.thewind.space.main.ui.music.detailpage.services

import android.util.Log
import com.thewind.space.api.base.RetrofitDefault
import com.thewind.space.main.ui.music.model.MusicPlayInfo


/**
 * @author: read
 * @date: 2022/12/11 下午9:23
 * @description:
 */

private const val TAG = "[App]MusicPlayHelper"

fun getPlayInfo(shareId: String): List<MusicPlayInfo> {
    try {
        return RetrofitDefault.create(MusicPlayUrlService::class.java).getPlayUrl(shareId)?.execute()?.body()
            ?: emptyList()
    } catch (e: java.lang.Exception) {
        Log.i(TAG, "getPlayUrl, exception = $e")
    }
    return emptyList()
}