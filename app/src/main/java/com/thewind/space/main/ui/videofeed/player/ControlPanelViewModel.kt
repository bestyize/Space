package com.thewind.space.main.ui.videofeed.player

import androidx.lifecycle.ViewModel
import com.thewind.space.R

/**
 * @author: read
 * @date: 2022/12/4 下午2:45
 * @description:
 */
class ControlPanelViewModel : ViewModel() {

    //    val mOperateElement: MutableLiveData<List<Pair<Int, String>>> = MutableLiveData()
//
    fun loadElements(): MutableList<Pair<Int, String>> {
        return ControlPanelModel.mPanelElements
    }
}

object ControlPanelModel {
    val mPanelElements = mutableListOf(
        Pair(R.drawable.ic_like, "喜欢"),
        Pair(R.drawable.ic_comment, "评论"),
        Pair(R.drawable.ic_collect, "收藏"),
        Pair(R.drawable.ic_share, "分享")
    )
}

enum class ControlPanelElement(value: Int) {
    LIKE(0),
    COMMENT(1),
    COLLECT(2),
    SHARE(3);
}