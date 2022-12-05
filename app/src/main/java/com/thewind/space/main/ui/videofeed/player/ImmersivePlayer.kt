package com.thewind.space.main.ui.videofeed.player

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.FrameLayout
import com.thewind.space.R
import com.thewind.space.main.ui.videofeed.control.ControlPanelView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2022/12/4 下午5:16
 * @description:
 */
class ImmersivePlayerView(context: Context, attr: AttributeSet) : FrameLayout(context, attr) {

    private var mTextureView: TextureView? = null
    private var mPLayer: MediaPlayer? = null
    private var mControlPlayerView: ControlPanelView?  = null

    var playUrl: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.player_layout, this)
    }

    private fun adjustVideoScale(videoWidth: Int, videoHeight: Int) {
        val lp = mTextureView?.layoutParams
        lp?.height = (width.toFloat() * (videoHeight.toFloat() / videoWidth.toFloat())).toInt()
        mTextureView?.layoutParams = lp
    }

    fun start() {
        initPlayer()
    }

    fun pause() {
        if (mPLayer?.isPlaying == true) {
            mPLayer?.pause()
        }
    }

    fun stop() {
        if (mPLayer?.isPlaying == true) {
            mPLayer?.stop()
        }
    }

    fun release() {
        mPLayer?.reset()
        mPLayer?.release()
        mPLayer = null

    }

    fun initPlayer() {
        mPLayer = MediaPlayer()
        mTextureView = findViewById(R.id.texture_view)
        mTextureView?.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                mPLayer?.setSurface(Surface(surface))
                MainScope().launch {
                    launch(Dispatchers.IO) {
                        playUrl?.let { url ->
                            mPLayer?.setDataSource(url)
                            mPLayer?.prepareAsync()
                        }

                    }
                }
                mPLayer?.setOnPreparedListener {
                    adjustVideoScale(it.videoWidth, it.videoHeight)
                    it.start()
                }
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

        }
        mControlPlayerView = ControlPanelView(context).apply {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setOnClickListener {
            }
        }
        addView(mControlPlayerView)
    }


    companion object {
        private const val TAG = "[App]ImmersivePlayer"
    }

}