package com.thewind.space.main.ui.music.detailpage.ui.player.floatplayer

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.main.ui.music.detailpage.ui.MusicPlayActivity
import com.thewind.space.main.ui.music.detailpage.ui.player.MusicPlayerManager
import com.thewind.space.main.ui.music.model.MusicPlayInfo
import com.thewind.spacecore.extension.dp
import com.thewind.spacecore.uiutil.ViewUtils
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx

/**
 * @author: read
 * @date: 2022/12/13 上午1:39
 * @description:
 */

private const val TAG = "[App]FloatPlayerManager"

class FloatPlayerManager {

    private var mFloatPlayerView: FloatPlayerView ?= null

    private fun generatePlayerView(
        context: Context,
        activity: BaseActivity,
        musicPlayInfo: MusicPlayInfo
    ): FloatPlayerView {
        return FloatPlayerView(context).apply {
            playInfo = musicPlayInfo
            layoutParams = FrameLayout.LayoutParams((ViewUtils.getScreenWidth() * 0.7).toInt() , dpToPx(60)).apply {
                gravity = Gravity.BOTTOM or Gravity.CENTER
                bottomMargin = dpToPx(60)
            }
            setCardBackgroundColor(context.resources.getColor(R.color.background_gray))
            cardElevation = 5.dp()
            radius = 10.dp()
            fListener = object : FloatPlayerListener {
                override fun onClick() {
                    val intent = Intent(activity, MusicPlayActivity::class.java)
                    intent.putExtra("music_info", MusicPlayerManager.getInstance().getMusicInfo())
                    intent.putExtra(
                        "music_play_info",
                        MusicPlayerManager.getInstance().getPlayerData()
                    )
                    activity.startActivity(intent)
                }

                override fun onPlayButtonClick() {
                    if (MusicPlayerManager.getInstance().getPlayer()?.isPlaying == true) {
                        MusicPlayerManager.getInstance().getPlayer()?.pause()
                    } else {
                        MusicPlayerManager.getInstance().getPlayer()?.start()
                    }
                    refresh()
                }
            }
        }
    }

    fun addPlayerView(activity: BaseActivity, rootView: ViewGroup) {
        activity.lifecycle.addObserver(object : LifecycleEventObserver{
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        Log.i(TAG, "addPlayerView, on resume")
                        MusicPlayerManager.getInstance().getPlayerData()?.let {
                            mFloatPlayerView = generatePlayerView(rootView.context, activity, it)
                            rootView.addView(mFloatPlayerView)
                        }
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        Log.i(TAG, "addPlayerView, on pause")
                        mFloatPlayerView?.let { v ->
                            mLastX = v.x
                            mLastY = v.y
                            (v.parent as? ViewGroup)?.removeView(mFloatPlayerView)
                        }
                        mFloatPlayerView = null

                    }
                    else -> {}
                }
            }
        })


    }

    companion object {

        private var mLastX = 0f
        private var mLastY = 0f

        fun getInstance(): FloatPlayerManager {
            return Inner.INSTANCE
        }
    }

    private object Inner {
        val INSTANCE = FloatPlayerManager()
    }
}