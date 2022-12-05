package com.thewind.space.main.ui.videofeed.feed.view

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: read
 * @date: 2022/12/4 下午10:56
 * @description:
 */
class ImmersivePageHelper(
    private var recyclerView: RecyclerView
) : RecyclerView.OnScrollListener() {

    private val mPageSnapHelper = PagerSnapHelper()

    private var pageChangeListener: OnPageChangeListener ?= null


    private var mPrevPos: Int = -1

    init {
        mPageSnapHelper.attachToRecyclerView(recyclerView)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        pageChangeListener?.onScrolled(recyclerView, dx, dy)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        var pos = 0
        mPageSnapHelper.findSnapView(recyclerView.layoutManager)?.let { view ->
            pos = recyclerView.layoutManager?.getPosition(view) ?: 0
            pageChangeListener?.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mPrevPos != pos) {
                mPrevPos = pos
                pageChangeListener?.onPageSelected(pos)
            }
        }

    }


}

interface OnPageChangeListener {
    fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}

    fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}

    fun onPageSelected(pos: Int)
}