package com.thewind.space.main.ui.music.searchpage.ui


import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter
import com.thewind.space.databinding.ActivityMusicSearchBinding
import com.thewind.space.main.ui.music.detailpage.ui.player.floatplayer.FloatPlayerManager
import com.thewind.spacecore.uiutil.ViewUtils

@Route(path = AppRouter.PathDefine.MUSIC_SEARCH_PAGE)
class MusicSearchActivity : BaseActivity() {

    private lateinit var binding: ActivityMusicSearchBinding
    private var searchFrag = MusicSearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FloatPlayerManager.getInstance().addPlayerView(this, binding.root)
        ViewUtils.enterFullScreenMode(this, true)
        supportFragmentManager.beginTransaction().replace(R.id.fcv_container_music_activity, searchFrag).commitNowAllowingStateLoss()
    }
}