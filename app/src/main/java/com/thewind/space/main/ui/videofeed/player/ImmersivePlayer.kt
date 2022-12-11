package com.thewind.space.main.ui.videofeed.player

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.FrameLayout
import com.thewind.space.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2022/12/4 下午5:16
 * @description:
 */
class ImmersivePlayer(context: Context, attr: AttributeSet) : FrameLayout(context, attr) {

    private var mTextureView: TextureView? = null
    private var mPLayer: MediaPlayer? = null
    private var mSurface: Surface? = null
    private var mControlPlayerView: ControlPanelView? = null

    private var mCurrentState: PlayerState = PlayerState.INIT

    var operationListener: ImmersivePlayerOperationListener? = null
    var stateListener: ImmersivePlayerStateListener? = null
    var onErrorListener: MediaPlayer.OnErrorListener =
        MediaPlayer.OnErrorListener { mp, what, extra ->
            Log.i(TAG, "onError, what = $what, extra = $extra")
            true
        }

    var playerUpdateListener: ImmersivePlayerUpdateListener ?= null

    var playUrl: String? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.player_layout, this)
    }

    private fun adjustVideoScale(videoWidth: Int, videoHeight: Int) {
        val lp = mTextureView?.layoutParams
        lp?.height = (width.toFloat() * (videoHeight.toFloat() / videoWidth.toFloat())).toInt()
        mTextureView?.layoutParams = lp
    }

    fun prepare() {
        mCurrentState = PlayerState.PREPARING
        stateListener?.onStateChanged(mCurrentState)
        initPlayer()
    }

    fun start() {
        Log.i(TAG, "start, url = $playUrl")
        mPLayer?.let {
            if (!it.isPlaying) {
                startCount()
                it.start()
                mCurrentState = PlayerState.PLAYING
                stateListener?.onStateChanged(mCurrentState)
            }
        }
    }

    private fun startCount() {
        MainScope().launch {
            while (mCurrentState != PlayerState.INIT) {
                when(mCurrentState) {
                    PlayerState.PLAYING -> {
                        val pos = getCurrPosition()
                        Log.i(TAG, "onProgressUpdate, pos = $pos")
                        playerUpdateListener?.onProgressUpdate(pos)
                    }
                    PlayerState.COMPLETED -> {
                        playerUpdateListener?.onProgressUpdate(getCurrPosition())
                        break
                    }
                    PlayerState.PAUSE -> {
                        playerUpdateListener?.onProgressUpdate(getCurrPosition())
                        break
                    }
                    else -> {
                        break
                    }
                }
                delay(200)
            }
        }
    }

    fun pause() {
        Log.i(TAG, "pause, url = $playUrl")
        if (mPLayer?.isPlaying == true) {
            mPLayer?.pause()
            mCurrentState = PlayerState.PAUSE
            stateListener?.onStateChanged(mCurrentState)
        }
    }

    fun stop() {
        Log.i(TAG, "stop, url = $playUrl")
        if (mPLayer?.isPlaying == true) {
            mPLayer?.stop()
            mCurrentState = PlayerState.STOP
            stateListener?.onStateChanged(mCurrentState)
        }
    }

    fun release() {
        Log.i(TAG, "release, url = $playUrl")
        mSurface?.release()
        mSurface = null
        mPLayer?.reset()
        mPLayer?.release()
        mPLayer = null
        mCurrentState = PlayerState.INIT
        stateListener?.onStateChanged(mCurrentState)

    }

    fun seekTo(timeMs: Int) {
        mPLayer?.seekTo(timeMs)
    }

    private fun getCurrPosition(): Int {
        return mPLayer?.currentPosition ?: 0
    }

    fun getDuration(): Int {
        return mPLayer?.duration?:0
    }

    private fun initPlayer() {
        Log.i(TAG, "initPlayer, url = $playUrl")
        mPLayer = MediaPlayer()
        mPLayer?.setOnErrorListener(onErrorListener)
        mTextureView = findViewById(R.id.texture_view)
        mTextureView?.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                Log.i(TAG, "onSurfaceTextureAvailable, url = $playUrl")
                mSurface = Surface(surface)
                mPLayer?.setSurface(mSurface)
                MainScope().launch {
                    launch(Dispatchers.IO) {
                        playUrl?.let { url ->
                            mPLayer?.setDataSource(url)
                            mPLayer?.prepareAsync()
                            mPLayer?.setOnPreparedListener {
                                Log.i(TAG, "onPrepared, url = $playUrl, duration = ${getDuration()}")
                                mCurrentState = PlayerState.PREPARED
                                stateListener?.onStateChanged(mCurrentState)
                                adjustVideoScale(it.videoWidth, it.videoHeight)
                                start()
                            }
                            mPLayer?.setOnCompletionListener {
                                Log.i(TAG, "onPLayCompletion, player state = ${mPLayer?.isPlaying}")
                                mCurrentState = PlayerState.COMPLETED
                            }
                            mPLayer?.setOnBufferingUpdateListener { _, percent ->
                                Log.i(
                                    TAG,
                                    "onBufferingUpdate, percent = $percent"
                                )
                            }

                        }

                    }

                }
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                Log.i(TAG, "onSurfaceTextureDestroyed, url = $playUrl")
                release()
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

        }
        mControlPlayerView = ControlPanelView(context, null).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            listener = operationListener
            setOnClickListener {
                when(mCurrentState) {
                    PlayerState.PLAYING -> {
                        pause()
                    }
                    PlayerState.PAUSE -> {
                        start()
                    }
                    PlayerState.STOP,
                    PlayerState.COMPLETED -> {
                        start()
                    }
                    else -> {

                    }
                }
                operationListener?.onCoverClicked()
            }
        }
        addView(mControlPlayerView)
    }


    companion object {
        private const val TAG = "[App]ImmersivePlayer"
    }

}

enum class PlayerState(value: Int) {
    INIT(0),
    PREPARING(1),
    PREPARED(2),
    PLAYING(3),
    PAUSE(4),
    STOP(5),
    COMPLETED(6)
}