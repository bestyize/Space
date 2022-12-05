package com.thewind.space.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.thewind.basic.app.SpaceApp

/**
 * @author: read
 * @date: 2022/12/4 上午12:01
 * @description:
 */
class SpaceApplication: Application() {

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
        Log.i(TAG, "init, finish init")
    }
}