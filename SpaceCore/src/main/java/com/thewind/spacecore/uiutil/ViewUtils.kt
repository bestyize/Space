package com.thewind.spacecore.uiutil

import android.app.Activity
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.thewind.basic.app.SpaceApp

/**
 * @author: read
 * @date: 2022/12/4 上午1:59
 * @description:
 */
object ViewUtils {

    fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            SpaceApp.application.resources.displayMetrics
        ).toInt()
    }

    fun getScreenWidth(): Int {
        return SpaceApp.application.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return SpaceApp.application.resources.displayMetrics.heightPixels
    }

    fun getScreenHeightDivWidth(): Float {
        return getScreenHeight().toFloat() / getScreenWidth().toFloat()
    }

    fun enterFullScreenMode(activity: Activity?) {
        val flags =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        activity?.window?.decorView?.systemUiVisibility = flags
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }
}