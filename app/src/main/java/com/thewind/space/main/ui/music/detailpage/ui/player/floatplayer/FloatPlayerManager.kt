package com.thewind.space.main.ui.music.detailpage.ui.player.floatplayer

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter
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

    private var mFloatPlayerView: FloatPlayerView? = null

    private fun generatePlayerView(
        context: Context,
        musicPlayInfo: MusicPlayInfo,
        listener: FloatPlayerListener
    ): FloatPlayerView {
        return FloatPlayerView(context).apply {
            playInfo = musicPlayInfo
            layoutParams =
                FrameLayout.LayoutParams((ViewUtils.getScreenWidth() * 0.7).toInt(), dpToPx(60))
                    .apply {
                        gravity = Gravity.BOTTOM or Gravity.CENTER
                        bottomMargin = 100.dp().toInt()
                    }
            setCardBackgroundColor(context.resources.getColor(R.color.background_gray))
            cardElevation = 5.dp()
            radius = 10.dp()
            fListener = listener
        }
    }

    fun addPlayerView(activity: BaseActivity, rootView: ViewGroup) {
        activity.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        Log.i(TAG, "addPlayerView, on resume")
                        MusicPlayerManager.getInstance().getPlayerData()?.let {
                            mFloatPlayerView = generatePlayerView(
                                rootView.context,
                                it,
                                object : FloatPlayerListener {
                                    override fun onClick() {
                                        ARouter.getInstance()
                                            .build(AppRouter.PathDefine.MUSIC_PLAYER_PAGE)
                                            .withObject(
                                                AppRouter.MusicPlayerDefine.MUSIC_PLAY_INFO,
                                                MusicPlayerManager.getInstance().getPlayerData()
                                            ).withObject(
                                            AppRouter.MusicPlayerDefine.MUSIC_INFO,
                                            MusicPlayerManager.getInstance().getMusicInfo()
                                        ).navigation()

                                    }

                                    override fun onPlayButtonClick() {
                                        if (MusicPlayerManager.getInstance()
                                                .getPlayer()?.isPlaying == true
                                        ) {
                                            MusicPlayerManager.getInstance().getPlayer()?.pause()
                                        } else {
                                            MusicPlayerManager.getInstance().getPlayer()?.start()
                                        }
                                        mFloatPlayerView?.refresh()
                                    }

                                    override fun onCloseClick() {
                                        MusicPlayerManager.getInstance().getPlayer()?.release()
                                        mFloatPlayerView?.visibility = View.GONE
                                        (mFloatPlayerView?.parent as? ViewGroup)?.removeView(
                                            mFloatPlayerView
                                        )
                                        mFloatPlayerView = null
                                    }
                                })
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