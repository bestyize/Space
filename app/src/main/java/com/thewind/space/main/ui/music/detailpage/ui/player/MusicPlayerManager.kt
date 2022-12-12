package com.thewind.space.main.ui.music.detailpage.ui.player

import android.media.MediaPlayer
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.MusicPlayInfo

/**
 * @author: read
 * @date: 2022/12/11 下午9:38
 * @description:
 */
class MusicPlayerManager {

    private var mMusicPlayInfo: MusicPlayInfo? = null
    private var mMusicInfo: MusicInfo ?= null



    private var mediaPlayer: MediaPlayer? = MediaPlayer()

    fun getPlayer(): MediaPlayer? {
        return mediaPlayer
    }

    fun release(){
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer =null
        mediaPlayer = MediaPlayer()
    }

    fun setPlayerData(musicPlayInfo: MusicPlayInfo?) {
        mMusicPlayInfo = musicPlayInfo
    }

    fun setMusicInfo(musicInfo: MusicInfo?) {
        mMusicInfo = musicInfo
    }

    fun getPlayerData(): MusicPlayInfo? {
        return mMusicPlayInfo
    }

    fun getMusicInfo(): MusicInfo? {
        return mMusicInfo
    }

    private object Inner {
        val INSTANCE = MusicPlayerManager()
    }

    companion object {
        fun getInstance() = Inner.INSTANCE
    }

}

enum class MusicPlayState(val state: Int) {
    STOP(0),
    INIT(1),
    PREPARED(2),
    PLAYING(3),
    PAUSED(4),
    COMPLETED(5)
}