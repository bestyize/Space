package com.thewind.space.main.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.doOnPreDraw
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.databinding.ActivityMainBinding
import com.thewind.space.detailpage.videodetailpage.VideoDetailActivity
import com.thewind.space.main.ui.bottomnav.BottomBarViewModel
import com.thewind.space.main.ui.bottomnav.BottomNavBarView
import com.thewind.space.main.ui.recommand.RecommendFragment
import com.thewind.space.main.ui.videofeed.VideoFeedFragment

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
            binding.root.addView(bottomNavBarView)
        }
        binding.root.doOnPreDraw {
            bottomBarVm.update()
        }
    }

    override fun onSelect(index: Int) {
        Log.i(TAG, "index  = $index is selected")
        if (index == 0) {
            val recommendFeedFragment = RecommendFragment()
            supportFragmentManager.beginTransaction().add(R.id.frag_container, recommendFeedFragment).commitNowAllowingStateLoss()
        } else if (index == 2) {
            val videoFeed = VideoFeedFragment()
            //supportFragmentManager.beginTransaction().replace(R.id.frag_container, videoFeed).commitNowAllowingStateLoss()
            supportFragmentManager.beginTransaction().replace(R.id.frag_container, videoFeed).commitNowAllowingStateLoss()
        } else if (index == 3) {
            val intent = Intent(this, VideoDetailActivity::class.java)
            startActivity(intent)
        } else if (index == 4) {
            ARouter.getInstance().build("/video/detail").navigation()
        }
    }
}