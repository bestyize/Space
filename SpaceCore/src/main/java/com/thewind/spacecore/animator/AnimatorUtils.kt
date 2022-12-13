package com.thewind.spacecore.animator

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @author: read
 * @date: 2022/12/14 上午1:33
 * @description:
 */
object AnimatorUtils {

    fun startRotate(view: View) {
        ObjectAnimator.ofFloat(view, "rotation", 0f, 360f).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()
            duration = 6000
            start()
        }
    }

}