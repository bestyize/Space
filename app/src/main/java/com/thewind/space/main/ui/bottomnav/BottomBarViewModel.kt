package com.thewind.space.main.ui.bottomnav

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author: read
 * @date: 2022/12/4 上午3:12
 * @description:
 */
class BottomBarViewModel : ViewModel() {

    var tabs  = MutableLiveData<List<String>>()

    var backColor = MutableLiveData<Int>()

    fun update() {
        tabs.postValue(BottomBarModel.tabList)
    }


}

object BottomBarModel {
    val tabList = listOf("首页", "音乐", "视频", "论坛", "我的")
}