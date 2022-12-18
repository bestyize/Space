package com.thewind.space.main.ui

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter
import com.thewind.space.databinding.ActivityMainBinding
import com.thewind.space.main.ui.bottomnav.BottomBarViewModel
import com.thewind.space.main.ui.bottomnav.BottomNavBarView
import com.thewind.space.main.ui.define.MainPage
import com.thewind.space.main.ui.music.detailpage.ui.player.floatplayer.FloatPlayerManager
import com.thewind.space.main.ui.music.indexpage.IndexFragment
import com.thewind.space.main.ui.videofeed.VideoFeedFragment
import com.thewind.spacecore.notify.ToastHelper
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "[App]MainActivity"

class MainActivity : BaseActivity(), BottomNavBarView.BottomNavBarViewSelectListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavBarView: BottomNavBarView

    private val mPageMap: Map<Int, Fragment> = mapOf(
        MainPage.RECOMMEND_PAGE.value to IndexFragment(),
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
                    layoutParams =
                        FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, dpToPx(1)).apply {
                            gravity = Gravity.TOP
                        }
                    setColor(context.getColor(R.color.light_gray))
                }
            }
            binding.root.addView(sepView)
            binding.root.addView(bottomNavBarView)
        }
        binding.root.doOnPreDraw {
            bottomBarVm.update()
        }
    }

    override fun onSelect(index: Int) {
        Log.i(TAG, "index  = $index is selected")
        when (index) {
            MainPage.RECOMMEND_PAGE.value -> {
                mPageMap[index]?.let {
                    bottomNavBarView.setColor(Color.WHITE, Color.BLACK, Color.GRAY)
                    supportFragmentManager.beginTransaction().replace(R.id.frag_container, it)
                        .commitNowAllowingStateLoss()
                }

            }
            MainPage.VIDEO_FEED_PAGE.value -> {
                mPageMap[index]?.let {
                    bottomNavBarView.setColor(Color.TRANSPARENT, Color.RED, Color.WHITE)
                    supportFragmentManager.beginTransaction().replace(R.id.frag_container, it)
                        .commitNowAllowingStateLoss()
                }
            }
            MainPage.TALK_PAGE.value -> {
                ARouter.getInstance().build(AppRouter.PathDefine.VIDEO_DETAIL_PAGE).navigation()
            }
            MainPage.USER_CENTER_PAGE.value -> {
                ARouter.getInstance().build(AppRouter.PathDefine.VIDEO_DETAIL_PAGE).navigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MainScope().launch {
            delay(5000)
            requestPermission()
        }

    }

    private fun requestPermission() {
        // 通过api判断手机当前版本号
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 安卓11，判断有没有“所有文件访问权限”权限
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:" + application.packageName)
                startActivityForResult(intent, 100)
            }
        } else {
            // 安卓6 判断有没有读写权限权限
            if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) {
                return
            }
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
                ),
                100
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ToastHelper.toast("存储权限获取失败")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                ToastHelper.toast("存储权限获取失败")
            }
        }
    }

}