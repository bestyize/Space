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
import com.thewind.spacecore.animator.AnimatorUtils
import com.thewind.spacecore.extension.dp
import com.thewind.spacecore.uiutil.ViewUtils
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx
import kotlin.math.abs

/**
 * @author: read
 * @date: 2022/12/13 上午1:16
 * @description:
 */
class FloatPlayerView(context: Context, attr: AttributeSet? = null) : CardView(context, attr) {

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
            this.x = dpToPx(16).toFloat()
        }
        mMusicTitleView = TextView(context).apply {
            layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = dpToPx(72)
                }
            textAlignment = View.TEXT_ALIGNMENT_GRAVITY
            isSingleLine = true
            maxEms = 5
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Color.BLACK)
            textSize = 14.toFloat()
        }
        mMusicTitleView.post {
            mSingersView.apply {
                this.x = (mMusicTitleView.x + mMusicTitleView.width + dpToPx(10))
            }
        }
        mPlayerButtonView = ImageView(context).apply {
            layoutParams = LayoutParams(dpToPx(30), dpToPx(36)).apply {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
                marginEnd = dpToPx(16)
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
            maxEms = 3
            ellipsize = TextUtils.TruncateAt.END
            setTextColor(Color.BLACK)
            textSize = 12.toFloat()
        }
        addView(mSingersView)
    }

    private var mLastX: Float = 0f
    private var mLastY: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.onTouchEvent(null)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x
                mLastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (event.x - mLastX).toInt()
                val dy = (event.y - mLastY).toInt()
                if (abs(dx) > 10 || abs(dy) > 10) {
                    val l = left + dx
                    val r = l + width
                    val t = top + dy
                    val b = t + height

                    if (l >= -(0.1 * width)
                        && (r <= 1.5 * width)
                        && (t > 60.dp())
                        && (b <= ViewUtils.getScreenHeight())) {
                        layout(l, t, r, b)
                    }
                    return true
                }

            }
        }
        return true
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Glide.with(this).load(playInfo?.pic).centerCrop().placeholder(R.drawable.ic_music)
            .circleCrop().into(mCoverView)
        mMusicTitleView.text = playInfo?.title ?: ""
        mSingersView.text = playInfo?.author
        refresh()
    }

    fun refresh() {
        val isPlaying = MusicPlayerManager.getInstance().getPlayer()?.isPlaying == true
        mPlayerButtonView.setImageResource(if (isPlaying) R.drawable.play_to_play else R.drawable.play_to_pause)
        if (isPlaying) {
            AnimatorUtils.startRotate(mCoverView)
        } else {
            mCoverView.clearAnimation()
        }
    }


}

interface FloatPlayerListener {
    fun onClick()
    fun onPlayButtonClick()
}