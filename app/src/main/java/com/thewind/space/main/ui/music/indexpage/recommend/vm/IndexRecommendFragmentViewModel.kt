package com.thewind.space.main.ui.music.indexpage.recommend.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewind.space.main.ui.music.indexpage.model.RecommendCard
import com.thewind.space.main.ui.music.indexpage.recommend.services.getIndexRecommendCardList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2022/12/17 下午2:02
 * @description:
 */
class IndexRecommendFragmentViewModel: ViewModel() {

    val recommendFeeds = MutableLiveData<List<RecommendCard>>()

    val recommendFeedsMore = MutableLiveData<List<RecommendCard>>()

    private var refreshCount: Int = 0

    fun refresh(keyword: String, loadMore: Boolean = false) {
        val page = if (loadMore) refreshCount else 0
        if (loadMore) {
            refreshCount++
        } else {
            refreshCount = 0
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getIndexRecommendCardList(page, keyword)
            }.let {
                if (loadMore) recommendFeedsMore.postValue(it) else recommendFeeds.postValue(it)
            }
        }

    }


}