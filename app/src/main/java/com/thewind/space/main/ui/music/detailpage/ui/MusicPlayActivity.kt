package com.thewind.space.main.ui.music.detailpage.ui


import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter
import com.thewind.space.databinding.ActivityMusicPlayBinding
import com.thewind.space.main.ui.music.detailpage.ui.player.MusicPlayerFragment
import com.thewind.space.main.ui.music.detailpage.ui.player.MusicPlayerManager
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.MusicPlayInfo
import com.thewind.spacecore.uiutil.ViewUtils

@Route(path = AppRouter.PathDefine.MUSIC_PLAYER_PAGE)
class MusicPlayActivity : BaseActivity() {

    private lateinit var binding: ActivityMusicPlayBinding

    @Autowired(name = AppRouter.MusicPlayerDefine.MUSIC_INFO)
    @JvmField
    var musicInfo: MusicInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        musicInfo?.let { info ->
            if (info.songId != MusicPlayerManager.getInstance().getMusicInfo()?.songId) {
                MusicPlayerManager.getInstance().release()
            }
        }
        binding = ActivityMusicPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewUtils.enterFullScreenMode(this, false)
        initView()

    }

    private fun initView() {
        musicInfo?.let {
            supportFragmentManager.beginTransaction().replace(
                R.id.frag_container,
                MusicPlayerFragment.newInstance(it)
            ).commitNowAllowingStateLoss()
        }

    }
}