package com.thewind.basic.app

import android.app.Application

/**
 * @author: read
 * @date: 2022/12/4 上午12:10
 * @description:
 */
object SpaceApp {

    lateinit var application: Application

    fun init(app: Application) {
        application = app
    }

}