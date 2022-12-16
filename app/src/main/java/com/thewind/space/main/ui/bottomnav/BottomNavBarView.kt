package com.thewind.space.main.ui.bottomnav

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import com.thewind.space.R
import com.thewind.spacecore.extension.dp
import com.thewind.spacecore.uiutil.ViewUtils

/**
 * @author: read
 * @date: 2022/12/4 上午12:53
 * @description:
 */

private const val TAG = "[App]BottomNavBarView"

class BottomNavBarView(context: Context): FrameLayout(context) {

    companion object {
        fun createDefaultBottomNavBar(context: Context, list: List<String>, listener: BottomNavBarViewSelectListener): BottomNavBarView {
            return BottomNavBarView(context).apply {
                mHeight = ViewUtils.dpToPx(57)
                mBackgroundColor = context.getColor(R.color.white)
                mSelectTextColor = context.getColor(R.color.light_blue_A400)
                mUnSelectTextColor = context.getColor(R.color.black)
                mSelectListener = listener
                //mShadowColor = Color.RED
                mShadowSize = 3.dp().toInt()
                addItems(list)
            }
        }
    }

    var mHeight: Int = 0
    set(value) {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value).apply {
            gravity = Gravity.BOTTOM
        }
        field = value
    }
    var mBackgroundColor: Int = 0
    var mSelectTextColor: Int = 0
    var mUnSelectTextColor: Int = 0
    var mSelectListener: BottomNavBarViewSelectListener? = null
    var mShadowColor: Int = 0x33333333.toInt()
    var mShadowSize: Int = 0.dp().toInt()
    private var mShadowPainter: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = mShadowColor
    }

    private val mNavItems = mutableListOf<String>()
    private val mNavViews = mutableListOf<TextView>()

    private var mSelectedIndex: Int = 0

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.drawColor(mBackgroundColor)
        (0..mShadowSize).forEach {
            mShadowPainter.alpha = it * 16 / mShadowSize
            canvas?.drawLine(0f, (1 + it).toFloat(), width.toFloat(), (1 + it).toFloat(), mShadowPainter)
        }
        super.dispatchDraw(canvas)
    }

    fun addItems(items: List<String>) {
        mNavItems.addAll(items.filter { it.isNotBlank() })
        doOnPreDraw {
            mNavViews.clear()
            var index = 0
            val size = mNavItems.size
            mNavItems.forEach { item ->
                val tv = createTextView(index, item, width / size, height)
                Log.i(TAG, "tv.x = ${tv.x}, tv.text = ${tv.text}")
                mNavViews.add(tv)
                addView(tv)
                index++
            }

        }
    }

    fun setColor(@ColorInt bgColor: Int, @ColorInt selectTextColor: Int, @ColorInt unSelectTextColor: Int) {
        mBackgroundColor = bgColor
        mSelectTextColor = selectTextColor
        mUnSelectTextColor = unSelectTextColor
        refreshTextView()
        invalidate()
    }


    private fun createTextView(index: Int, title: String, w: Int, h: Int): TextView {
        return TextView(context).apply {
            x = (w * index).toFloat()
            text = title
            layoutParams = ViewGroup.LayoutParams(w, h)
            textAlignment = TEXT_ALIGNMENT_CENTER
            gravity = Gravity.CENTER
            setTextSize(TypedValue.COMPLEX_UNIT_PX, h.toFloat() / 3f)
            setOnClickListener {
                mSelectedIndex = index
                onSelect()
            }
            onSelect()
        }
    }

    private fun onSelect() {
        refreshTextView()
        mSelectListener?.onSelect(mSelectedIndex)
    }

    private fun refreshTextView() {
        var index = 0
        mNavViews.forEach {
            val textColor = if (index == mSelectedIndex) mSelectTextColor else mUnSelectTextColor
            it.setTextColor(textColor)
            it.typeface = if (index == mSelectedIndex) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            index++
        }
    }

    interface BottomNavBarViewSelectListener {
        fun onSelect(index: Int)
    }

}


