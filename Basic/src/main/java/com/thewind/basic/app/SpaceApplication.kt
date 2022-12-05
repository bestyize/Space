package com.thewind.basic.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter


/**
 * @author: read
 * @date: 2022/12/4 上午12:38
 * @description:
 */
class SpaceApplication : Application() {

    companion object {
        const val TAG = "[App]SpaceApplication"
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    private fun init() {
        Log.i(TAG, "init, begin init")
        SpaceApp.init(this)
        ARouter.init(this)
        Log.i(TAG, "init, finish init")
    }
}