package com.thewind.space.main.ui.music.detailpage.ui.player

import android.media.MediaPlayer

/**
 * @author: read
 * @date: 2022/12/11 下午9:38
 * @description:
 */
class MusicPlayerManager {

    companion object {
        fun getInstance() = Inner.INSTANCE
    }

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

    private object Inner {
        val INSTANCE = MusicPlayerManager()
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