package com.thewind.space.main.ui.videofeed.player

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.AudioManager.*
import android.os.Build
import com.thewind.basic.app.SpaceApp

/**
 * @author: read
 * @date: 2022/12/6 上午1:54
 * @description:
 */
class FeedAudioManager(private var focusChangeListener: OnAudioFocusChangeListener) {

    private var mAudioManager: AudioManager =
        SpaceApp.application.getSystemService(Context.AUDIO_SERVICE) as AudioManager


    /**
     *  请求获取音频焦点
     * @param focusChangeListener OnAudioFocusChangeListener
     */
    fun requestFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioRequest = AudioFocusRequest.Builder(AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(focusChangeListener).setAudioAttributes(
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build()
            ).build()
            mAudioManager.requestAudioFocus(audioRequest)
        } else {
            mAudioManager.requestAudioFocus(focusChangeListener, STREAM_MUSIC, AUDIOFOCUS_GAIN)
        }
    }

    /**
     * 请求释放音频焦点
     * @param focusChangeListener OnAudioFocusChangeListener
     */
    fun releaseFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioRequest = AudioFocusRequest.Builder(AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(focusChangeListener).setAudioAttributes(
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build()
            ).build()
            mAudioManager.abandonAudioFocusRequest(audioRequest)
        } else {
            mAudioManager.abandonAudioFocus(focusChangeListener)
        }
    }

}