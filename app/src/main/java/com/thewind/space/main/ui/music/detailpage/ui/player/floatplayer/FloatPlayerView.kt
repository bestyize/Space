package com.thewind.space.main.ui.music.detailpage.ui.player.floatplayer

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.thewind.space.R
import com.thewind.space.main.ui.music.detailpage.ui.player.MusicPlayerManager
import com.thewind.space.main.ui.music.model.MusicPlayInfo
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx

/**
 * @author: read
 * @date: 2022/12/13 上午1:16
 * @description:
 */
class FloatPlayerView(context: Context, attr: AttributeSet? = null):  CardView(context, attr) {

    private var mCoverView: ImageView
    private var mMusicTitleView: TextView
    private lateinit var mSingersView: TextView
    private var mPlayerButtonView: ImageView
    var fListener: FloatPlayerListener? = null

    var playInfo: MusicPlayInfo? = null

    init {
        mCoverView = ImageView(context).apply {
            layoutParams = LayoutParams(dpToPx(42), dpToPx(42)).apply {
                gravity = Gravity.CENTER_VERTICAL
            }
            this.x = dpToPx(20).toFloat()
        }
        mMusicTitleView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = dpToPx(72)
                }
            textAlignment = View.TEXT_ALIGNMENT_GRAVITY
            isSingleLine = true
            maxEms = 12
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Color.BLACK)
            textSize = 16.toFloat()
        }
        mMusicTitleView.post {
            mSingersView.apply {
                this.x = (mMusicTitleView.x + mMusicTitleView.width + dpToPx(10))
            }
        }
        mPlayerButtonView = ImageView(context).apply {
            layoutParams = LayoutParams(dpToPx(36), dpToPx(36)).apply {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
                marginEnd = dpToPx(20)
            }
        }
        addView(mCoverView)
        addView(mMusicTitleView)
        addView(mPlayerButtonView)
        mCoverView.setOnClickListener {
            fListener?.onClick()
        }
        mPlayerButtonView.setOnClickListener {
            fListener?.onPlayButtonClick()
        }
        mSingersView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER_VERTICAL
                }
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            isSingleLine = true
            maxEms = 12
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Color.BLACK)
            textSize = 14.toFloat()
        }
        addView(mSingersView)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Glide.with(this).load(playInfo?.pic).centerCrop().circleCrop().into(mCoverView)
        mMusicTitleView.text = playInfo?.title ?: ""
        mSingersView.text = playInfo?.author
        refresh(MusicPlayerManager.getInstance().getPlayer()?.isPlaying == true)
    }

    fun refresh(isPlaying: Boolean = true) {
        mPlayerButtonView.setImageResource(if (isPlaying) R.drawable.play_to_play else R.drawable.play_to_pause)
    }

}

interface FloatPlayerListener {
    fun onClick()
    fun onPlayButtonClick()
}