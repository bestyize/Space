package com.thewind.spacecore.animator

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.animation.LinearInterpolator

/**
 * @author: read
 * @date: 2022/12/14 上午1:33
 * @description:
 */
object AnimatorUtils {

    fun startRotate(view: View, time: Long = 6000): Animator {
        val ani = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f).apply {
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()
            duration = time
        }
        view.addOnAttachStateChangeListener(object : OnAttachStateChangeListener{
            override fun onViewAttachedToWindow(v: View?) {
                ani.start()
            }

            override fun onViewDetachedFromWindow(v: View?) {
                ani.cancel()
            }
        })
        return ani

    }

}