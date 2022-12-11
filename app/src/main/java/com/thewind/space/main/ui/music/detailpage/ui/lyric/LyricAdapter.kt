package com.thewind.space.main.ui.music.detailpage.ui.lyric

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx

/**
 * @author: read
 * @date: 2022/12/11 下午11:54
 * @description:
 */
class LyricAdapter(val list: List<LyricInfo>): RecyclerView.Adapter<LyricViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricViewHolder {
        val view = TextView(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
                setPadding(dpToPx(5))
            }
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setTextSize(18.toFloat())
            setTextColor(Color.WHITE)
        }
        return LyricViewHolder(view)
    }

    override fun onBindViewHolder(holder: LyricViewHolder, position: Int) {
        holder.view.text = list[position].lyric ?: ""
        holder.view.tag = list[position].timeSecond
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class LyricViewHolder(val view: TextView): RecyclerView.ViewHolder(view) {

}