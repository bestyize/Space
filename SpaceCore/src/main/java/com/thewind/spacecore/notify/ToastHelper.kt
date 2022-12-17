package com.thewind.spacecore.notify

import android.widget.Toast
import com.thewind.basic.app.SpaceApp

/**
 * @author: read
 * @date: 2022/12/17 下午1:50
 * @description:
 */
object ToastHelper {

    fun toast(msg: String) {
        Toast.makeText(SpaceApp.application, msg, Toast.LENGTH_SHORT).show()
    }

}