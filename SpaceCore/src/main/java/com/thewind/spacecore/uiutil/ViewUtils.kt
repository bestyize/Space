package com.thewind.spacecore.uiutil

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
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

    fun enterImmersiveFullScreenMode(activity: Activity?) {
        activity ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window?.insetsController?.hide(WindowInsets.Type.systemBars())
        } else {
            val flags =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            activity.window?.decorView?.systemUiVisibility = flags
        }
    }

    fun enterFullScreenMode(activity: Activity?, isLight: Boolean) {
        activity ?: return
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        activity.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setStatusBarColor(activity, isLight)

    }

    fun exitFullScreenMode(activity: Activity?, isLight: Boolean) {
        activity ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window?.insetsController?.show(WindowInsets.Type.statusBars())
            activity.window?.statusBarColor = Color.TRANSPARENT
        }
        setStatusBarColor(activity, isLight)
    }

    private fun setStatusBarColor(activity: Activity?, isLight: Boolean) {
        val window = activity?.window
        window ?: return
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.fitsSystemWindows = true
        val compat = ViewCompat.getWindowInsetsController(activity.window.decorView)
        compat?.isAppearanceLightStatusBars = isLight
    }
}