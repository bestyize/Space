package com.thewind.space.main.ui.music.detailpage.ui.player

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.thewind.space.main.ui.music.detailpage.services.getPlayInfo
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.MusicPlayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2022/12/11 下午9:11
 * @description:
 */

private const val TAG = "App[ImmersiveMusicPlayerViewModel]"
class ImmersiveMusicPlayerViewModel {

    val playInfo: MutableLiveData<MusicPlayInfo> = MutableLiveData()

    fun loadMusic(musicInfo: MusicInfo?) {
        musicInfo ?: return
        MainScope().launch {
            withContext(Dispatchers.IO) {
                getPlayInfo(musicInfo.songSrc + "_" + musicInfo.songId)
            }.let {
                Log.i(TAG, "loadMusic, playInfo = $it")
                if (it.isNotEmpty()) {
                    playInfo.postValue(it[0])
                }
            }
        }
    }

    fun loadLyric() {

    }
}