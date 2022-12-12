package com.thewind.space.main.ui.music.detailpage.ui.player.floatplayer

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import com.thewind.basic.base.BaseActivity
import com.thewind.space.main.ui.music.detailpage.ui.MusicPlayActivity
import com.thewind.space.main.ui.music.detailpage.ui.player.MusicPlayerManager
import com.thewind.space.main.ui.music.model.MusicPlayInfo
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx

/**
 * @author: read
 * @date: 2022/12/13 上午1:39
 * @description:
 */
class FloatPlayerManager {

    private fun generatePlayerView(
        context: Context,
        activity: BaseActivity,
        musicPlayInfo: MusicPlayInfo
    ): FloatPlayerView {
        return FloatPlayerView(context).apply {
            playInfo = musicPlayInfo
            layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(60)).apply {
                gravity = Gravity.BOTTOM
                bottomMargin = dpToPx(60)
            }
            fListener = object : FloatPlayerListener {
                override fun onClick() {
                    MusicPlayerManager.getInstance().release()
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
                        refresh(false)
                        MusicPlayerManager.getInstance().getPlayer()?.pause()
                    } else {
                        refresh(true)
                        MusicPlayerManager.getInstance().getPlayer()?.start()
                    }
                }
            }
        }
    }

    fun addPlayerView(activity: BaseActivity, rootView: ViewGroup) {
        val playData = MusicPlayerManager.getInstance().getPlayerData()
        playData ?: return
        rootView.post {
            val view = generatePlayerView(rootView.context, activity, playData)
            rootView.addView(view)
        }

    }

    companion object {
        fun getInstance(): FloatPlayerManager {
            return Inner.INSTANCE
        }
    }

    private object Inner {
        val INSTANCE = FloatPlayerManager()
    }
}