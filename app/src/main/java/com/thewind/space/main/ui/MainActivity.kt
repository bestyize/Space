package com.thewind.space.main.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.databinding.ActivityMainBinding
import com.thewind.space.detailpage.videodetailpage.VideoDetailActivity
import com.thewind.space.main.ui.bottomnav.BottomBarViewModel
import com.thewind.space.main.ui.bottomnav.BottomNavBarView
import com.thewind.space.main.ui.define.MainPage
import com.thewind.space.main.ui.music.detailpage.ui.player.floatplayer.FloatPlayerManager
import com.thewind.space.main.ui.music.searchpage.ui.MusicSearchFragment
import com.thewind.space.main.ui.indexpage.RecommendFragment
import com.thewind.space.main.ui.videofeed.VideoFeedFragment
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx

private const val TAG = "[App]MainActivity"

class MainActivity : BaseActivity(), BottomNavBarView.BottomNavBarViewSelectListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavBarView: BottomNavBarView

    private val mPageMap: Map<Int, Fragment> = mapOf(
        MainPage.RECOMMEND_PAGE.value to RecommendFragment(),
        MainPage.MUSIC_PAGE.value to MusicSearchFragment(),
        MainPage.VIDEO_FEED_PAGE.value to VideoFeedFragment(),
    )


    private var bottomBarVm: BottomBarViewModel = BottomBarViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FloatPlayerManager.getInstance().addPlayerView(this, binding.root)
        initBottomBar()
    }


    private fun initBottomBar() {
        bottomBarVm.tabs.observe(this) {
            bottomNavBarView = BottomNavBarView.createDefaultBottomNavBar(this, it, this)
            val sepView = View(this).apply {
                background = GradientDrawable().apply {
                    layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(1)).apply {
                        gravity = Gravity.TOP
                    }
                setColor(context.getColor(R.color.light_gray))
            }}
            binding.root.addView(sepView)
            binding.root.addView(bottomNavBarView)
        }
        binding.root.doOnPreDraw {
            bottomBarVm.update()
        }
    }

    override fun onSelect(index: Int) {
        Log.i(TAG, "index  = $index is selected")
        when(index) {
            MainPage.RECOMMEND_PAGE.value -> {
                mPageMap[index]?.let {
                    bottomNavBarView.setColor(Color.WHITE, Color.BLUE, Color.BLACK)
                    supportFragmentManager.beginTransaction().replace(R.id.frag_container, it).commitNowAllowingStateLoss()
                }

            }
            MainPage.MUSIC_PAGE.value -> {
                mPageMap[index]?.let {
                    bottomNavBarView.setColor(Color.BLACK, Color.WHITE, Color.LTGRAY)
                    supportFragmentManager.beginTransaction().replace(R.id.frag_container, it).commitNowAllowingStateLoss()
                }
            }
            MainPage.VIDEO_FEED_PAGE.value -> {
                mPageMap[index]?.let {
                    bottomNavBarView.setColor(Color.TRANSPARENT, Color.RED, Color.WHITE)
                    supportFragmentManager.beginTransaction().replace(R.id.frag_container, it).commitNowAllowingStateLoss()
                }
            }
            MainPage.TALK_PAGE.value -> {
                val intent = Intent(this, VideoDetailActivity::class.java)
                startActivity(intent)
            }
            MainPage.USER_CENTER_PAGE.value -> {
                ARouter.getInstance().build("/video/detail").navigation()
            }
        }
    }
}