package com.thewind.space.main.ui.music.detailpage.ui.player

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.thewind.space.R
import com.thewind.space.databinding.FragmentMusicPlayerBinding
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.getSingerDisplayName
import com.thewind.space.main.ui.videofeed.player.ControlPanelView
import com.thewind.space.main.ui.videofeed.player.ImmersivePlayerOperationListener
import com.thewind.spacecore.formater.DateUtils
import com.thewind.spacecore.uiutil.FastBlurUtil
import com.thewind.spacecore.uiutil.ViewUtils
import kotlinx.coroutines.*
import kotlin.concurrent.timer


/**
 * A simple [Fragment] subclass.
 * Use the [MusicPlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "[App]MusicPlayerFragment"
class MusicPlayerFragment : Fragment(), ImmersivePlayerOperationListener {
    // TODO: Rename and change types of parameters
    var musicInfo: MusicInfo? = null
    private lateinit var binding: FragmentMusicPlayerBinding

    private val musicPlayerViewModel = ImmersiveMusicPlayerViewModel()

    private var mPlayState = MusicPlayState.STOP

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMusicPlayerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMusicTitle.text = musicInfo?.songName
        binding.tvSingersName.text = musicInfo?.getSingerDisplayName()

        musicPlayerViewModel.playInfo.observe(viewLifecycleOwner) { musicPlayInfo ->
            MainScope().launch {
                withContext(Dispatchers.IO) {
                    getPlayer()?.let { player ->
                        mPlayState = MusicPlayState.INIT
                        player.setDataSource(musicPlayInfo.url)
                        player.prepareAsync()
                        player.setOnPreparedListener {
                            mPlayState = MusicPlayState.PREPARED
                            binding.acsSeekBar.max = it.duration
                            player.seekTo(binding.acsSeekBar.progress)
                            startCountDown()
                            player.start()
                            mPlayState = MusicPlayState.PLAYING
                        }
                        player.setOnCompletionListener {
                            mPlayState = MusicPlayState.COMPLETED
                        }
                    }
                }
            }
        }
        Glide.with(this)
            .load(musicInfo?.coverUrl)
            .placeholder(R.drawable.ic_like)
            .into(binding.ivPlayerCover)
        Glide.with(this)
            .asBitmap()
            .load(musicInfo?.coverUrl)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    FastBlurUtil.doBlur(resource, 200, false)?.let {
                        binding.playerContainer.background = BitmapDrawable(it)
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })
        binding.root.addView(ControlPanelView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            listener = this@MusicPlayerFragment

        })
        binding.cvCenterCover.layoutParams = binding.cvCenterCover.layoutParams.apply {
            width = (ViewUtils.getScreenWidth() * 0.8).toInt()
            height = (ViewUtils.getScreenWidth() * 0.6).toInt()
        }
        binding.acsSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                getPlayer()?.seekTo(seekBar?.progress ?: 0)
                if (mPlayState.state > MusicPlayState.INIT.state) {
                    getPlayer()?.start()
                }
            }
        })
        musicPlayerViewModel.loadMusic(musicInfo)

    }

    private fun startCountDown() {
        MainScope().launch {
            while (mPlayState.state > MusicPlayState.INIT.state) {
                updateProgress()
                delay(1000)
            }
            if (mPlayState == MusicPlayState.COMPLETED) {
                updateProgress()
            }
        }
    }

    private fun updateProgress() {
        val currPos = getPlayer()?.currentPosition ?: 0
        binding.acsSeekBar.progress = getPlayer()?.currentPosition ?: 0
        binding.tvCurrPos.text = DateUtils.secondToMinSec(currPos / 1000)
        binding.tvDuration.text = DateUtils.secondToMinSec((getPlayer()?.duration ?: 0) / 1000)
    }


    private fun getPlayer() = MusicPlayerManager.getInstance().getPlayer()

    override fun onDestroyView() {
        super.onDestroyView()
        MusicPlayerManager.getInstance().release()
        mPlayState = MusicPlayState.STOP
    }

    companion object {

        @JvmStatic
        fun newInstance(mi: MusicInfo) =
            MusicPlayerFragment().apply {
                musicInfo = mi
            }
    }

    override fun onLikeClicked() {

    }

    override fun onCommentClicked() {

    }

    override fun onCollectClicked() {

    }

    override fun onShareClicked() {

    }

    override fun onCoverClicked() {
        Log.i(TAG, "onCoverClicked")
        if (getPlayer()?.isPlaying == true) {
            getPlayer()?.pause()
            mPlayState = MusicPlayState.PAUSED
        } else {
            getPlayer()?.start()
            mPlayState = MusicPlayState.PLAYING
        }
    }
}