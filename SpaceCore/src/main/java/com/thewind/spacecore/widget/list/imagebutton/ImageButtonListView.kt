package com.thewind.spacecore.widget.list.imagebutton

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.generateViewId
import com.bumptech.glide.Glide
import com.thewind.spacecore.uiutil.ViewUtils
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx


class ImageButtonListView(context: Context, items: MutableList<Pair<Int, String>>, listener: ImageButtonListClickListener) : FrameLayout(context) {

    private val mRecycleView: RecyclerView

    init {
        mRecycleView = RecyclerView(context).apply {
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutManager = LinearLayoutManager(context)
            adapter = ImageButtonListViewAdapter(items, listener)
        }
        addView(mRecycleView)
    }

    companion object {
        fun generateImageButtonListView(ctx: Context, items: MutableList<Pair<Int, String>>, listener: ImageButtonListClickListener): ImageButtonListView {
            return ImageButtonListView(ctx, items, listener)
        }
    }

}


class ImageButtonListViewAdapter(private val items: MutableList<Pair<Int, String>>, private val listener: ImageButtonListClickListener) :
    RecyclerView.Adapter<ImageButtonListViewAdapter.ViewHolder>() {

    private var mIvId: Int = 0
    private var mTitleId: Int = 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mImageView: ImageView = view.findViewById(mIvId)
        var mTitleView: TextView = view.findViewById(mTitleId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val commonWH = ViewUtils.dpToPx(40)
        val titleHeight = ViewUtils.dpToPx(20)
        val itemView = FrameLayout(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(commonWH, dpToPx(72)).apply {
                gravity = Gravity.CENTER
            }

        }
        mIvId = generateViewId()
        val iv = ImageView(parent.context).apply {
            id = mIvId
            layoutParams = FrameLayout.LayoutParams(commonWH, commonWH).apply {
                gravity = Gravity.CENTER
            }
        }
        mTitleId = generateViewId()
        val tv = TextView(parent.context).apply {
            id = mTitleId
            layoutParams = FrameLayout.LayoutParams(dpToPx(60), titleHeight).apply {
                gravity = Gravity.CENTER
            }
            textAlignment = TEXT_ALIGNMENT_CENTER
            y = commonWH.toFloat() - dpToPx(10)
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, (titleHeight / 2).toFloat())
        }
        itemView.addView(iv)
        itemView.addView(tv)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.itemView).load(item.first).fitCenter().into(holder.mImageView)
        holder.mTitleView.text = item.second

        holder.itemView.setOnClickListener {
            listener.onClicked(position, holder.mImageView, holder.mTitleView)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface ImageButtonListClickListener {
    fun onClicked(index: Int, imageView: ImageView, titleView: TextView)
}