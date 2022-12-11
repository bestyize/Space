package com.thewind.space.main.ui.videofeed.player

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import com.bumptech.glide.Glide
import com.thewind.space.R
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx
import com.thewind.spacecore.widget.list.imagebutton.ImageButtonListClickListener
import com.thewind.spacecore.widget.list.imagebutton.ImageButtonListView

/**
 * @author: read
 * @date: 2022/12/4 上午5:03
 * @description:
 */
class ControlPanelView(context: Context, attr: AttributeSet? = null) : FrameLayout(context, attr) {

    private val controlPanelViewModel = ControlPanelViewModel()

    var listener: ImmersivePlayerOperationListener? = null

    init {
        val itemView = ImageButtonListView.generateImageButtonListView(
            context,
            controlPanelViewModel.loadElements(),
            object : ImageButtonListClickListener {
                override fun onClicked(index: Int, imageView: ImageView, titleView: TextView) {
                    handleElementClicked(index, imageView, titleView)
                }
            }).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM or Gravity.END
                rightMargin = dpToPx(10)
                bottomMargin = dpToPx(80)
            }
        }

        addView(itemView)

        doOnPreDraw {
            val responseArea = FrameLayout(context).apply {
                layoutParams = LayoutParams((0.7 * width).toInt(), (0.7 * width).toInt()).apply {
                    gravity = Gravity.CENTER
                }
                setOnClickListener {
                    listener?.onCoverClicked()
                }
            }
            addView(responseArea)
        }

    }

    fun handleElementClicked(index: Int, imageView: ImageView, titleView: TextView) {
        Log.i(TAG, "handleElementClicked, index = $index， title = ${titleView.text}")
        if (index >= ControlPanelElement.values().size) {
            return
        }
        when (ControlPanelElement.values()[index]) {
            ControlPanelElement.LIKE -> {
                listener?.onLikeClicked()
                Glide.with(this).load(R.drawable.ic_liked).fitCenter().into(imageView)
            }
            ControlPanelElement.COMMENT -> {
                listener?.onCommentClicked()
            }
            ControlPanelElement.COLLECT -> {
                listener?.onCollectClicked()
                Glide.with(this).load(R.drawable.ic_collected).fitCenter().into(imageView)
            }
            ControlPanelElement.SHARE -> {
                listener?.onShareClicked()
                Toast.makeText(context, "点击分享", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val TAG = "[App]ControlPanelView"
    }
}