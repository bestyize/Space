package com.thewind.space.main.ui.videofeed.control

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginRight
import androidx.core.view.setPadding
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
class ControlPanelView(context: Context) : FrameLayout(context) {

    private val controlPanelViewModel = ControlPanelViewModel()

    init {
        val itemView = ImageButtonListView.generateImageButtonListView(context, controlPanelViewModel.loadElements(), object : ImageButtonListClickListener{
            override fun onClicked(index: Int, imageView: ImageView, titleView: TextView) {
                handleElementClicked(index, imageView, titleView)
            }
        }).apply {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.BOTTOM or Gravity.END
                rightMargin = dpToPx(10)
                bottomMargin = dpToPx(80)
            }
        }
        addView(itemView)

    }

    fun handleElementClicked(index: Int, imageView: ImageView, titleView: TextView) {
        Log.i(TAG, "handleElementClicked, index = $index， title = ${titleView.text}")
        if (index == 0) {
            Glide.with(this).load(R.drawable.ic_liked).fitCenter().into(imageView)
        } else if (index == 1) {
            Toast.makeText(context, "点击评论", Toast.LENGTH_SHORT).show()
        } else if (index == 2) {
            Glide.with(this).load(R.drawable.ic_collected).fitCenter().into(imageView)
        } else if (index == 3) {
            Toast.makeText(context, "点击分享", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val TAG = "[App]ControlPanelView"
    }
}