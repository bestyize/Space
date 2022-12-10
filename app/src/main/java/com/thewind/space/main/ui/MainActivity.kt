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
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.databinding.ActivityMainBinding
import com.thewind.space.detailpage.videodetailpage.VideoDetailActivity
import com.thewind.space.main.ui.bottomnav.BottomBarViewModel
import com.thewind.space.main.ui.bottomnav.BottomNavBarView
import com.thewind.space.main.ui.music.searchpage.ui.MusicSearchFragment
import com.thewind.space.main.ui.recommand.RecommendFragment
import com.thewind.space.main.ui.videofeed.VideoFeedFragment
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx

private const val TAG = "[App]MainActivity"

class MainActivity : BaseActivity(), BottomNavBarView.BottomNavBarViewSelectListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavBarView: BottomNavBarView


    private var bottomBarVm: BottomBarViewModel = BottomBarViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            0 -> {
                bottomNavBarView.setColor(Color.WHITE, Color.RED, Color.BLACK)
                val recommendFeedFragment = RecommendFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_container, recommendFeedFragment).commitNowAllowingStateLoss()
            }
            1 -> {
                bottomNavBarView.setColor(Color.WHITE, Color.RED, Color.BLACK)
                val musicSearchFragment = MusicSearchFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_container, musicSearchFragment).commitNowAllowingStateLoss()
            }
            2 -> {
                bottomNavBarView.setColor(Color.TRANSPARENT, Color.RED, Color.WHITE)
                val videoFeed = VideoFeedFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frag_container, videoFeed).commitNowAllowingStateLoss()
            }
            3 -> {
                val intent = Intent(this, VideoDetailActivity::class.java)
                startActivity(intent)
            }
            4 -> {
                ARouter.getInstance().build("/video/detail").navigation()
            }
        }
    }
}