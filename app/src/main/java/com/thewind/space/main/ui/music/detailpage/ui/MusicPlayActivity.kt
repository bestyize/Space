package com.thewind.space.main.ui.music.detailpage.ui


import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.databinding.ActivityMusicPlayBinding
import com.thewind.space.main.ui.music.detailpage.ui.player.MusicPlayerFragment
import com.thewind.space.main.ui.music.detailpage.ui.player.MusicPlayerManager
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.MusicPlayInfo
import com.thewind.spacecore.uiutil.ViewUtils

class MusicPlayActivity : BaseActivity() {

    private lateinit var binding: ActivityMusicPlayBinding
    private var musicInfo: MusicInfo? = null
    private var musicPlayInfo: MusicPlayInfo? = null

    private val mFrags: Map<Int, Fragment> = mapOf(
        1 to MusicPlayerFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewUtils.enterFullScreenMode(this, false)
        musicInfo = intent.extras?.get("music_info") as? MusicInfo
        musicPlayInfo = intent.extras?.get("music_play_info") as? MusicPlayInfo
        initView()

    }

    private fun initView() {
        musicInfo?.let {
            supportFragmentManager.beginTransaction().replace(R.id.frag_container, MusicPlayerFragment.newInstance(it)).commitNowAllowingStateLoss()
        }

    }
}