package com.thewind.space.main.ui.music.indexpage.recommend.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2022/12/18 下午5:05
 * @description:
 */
class IndexFragmentViewModel: ViewModel() {

    val tabs = MutableLiveData<List<String>>()

    fun requestTabs() {
        viewModelScope.launch {
            tabs.postValue(IndexFragmentDataProvider.tabs)
        }
    }

}

object IndexFragmentDataProvider {
    val tabs: List<String> = listOf("热门", "流行", "电子", "轻音乐", "民谣", "古风", "欧美", "摇滚")
}