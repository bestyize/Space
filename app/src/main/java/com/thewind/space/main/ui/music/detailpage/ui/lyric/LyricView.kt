package com.thewind.space.main.ui.music.detailpage.ui.lyric

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2022/12/11 下午11:46
 * @description:
 * //https://thewind.xyz/api/new/player?shareId=MG_6005751FRA4
 *
 * [00:01.00]歌曲名 我怀念的
[00:02.00]歌手名 孙燕姿
[00:03.00]作词：姚若龙
[00:04.00]作曲：李偲菘
 */

private const val TAG = "[App]LyricView"

class LyricView(context: Context, attr: AttributeSet ?= null) : NestedScrollView(context, attr) {

    private var mRvLyric: RecyclerView
    private var mAdapter: LyricAdapter
    private val mLyricList = mutableListOf<LyricInfo>()

    init {
        mAdapter = LyricAdapter(mLyricList)
        mRvLyric = RecyclerView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        addView(mRvLyric)
    }

    fun setLyric(string: String?) {
        string ?: return
        mLyricList.addAll(LyricUtils.parseLyric(string))
        mAdapter.notifyDataSetChanged()

    }

    fun update(timeSec: Int) {
        Log.i(TAG, "update, timeSec = $timeSec")
        var index = 0
        mLyricList.forEach {
            if (timeSec <= it.timeSecond) {
                val midIndex: Int = when {
                    index < 2 -> 0
                    else -> {index - 2}
                }
                //val scrollTime: Int = if(index >= mLyricList.size - 1) 3000 else (mLyricList[index + 1].timeSecond - mLyricList[index].timeSecond + 1000)
                smoothScrollTo(0, mAdapter.getItemHeight() * midIndex, 3600)
                return
            }
            index++
        }
    }
}