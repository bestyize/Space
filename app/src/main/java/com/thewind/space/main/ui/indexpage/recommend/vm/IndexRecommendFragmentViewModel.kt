package com.thewind.space.main.ui.indexpage.recommend.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thewind.space.main.ui.indexpage.model.RecommendCard
import com.thewind.space.main.ui.indexpage.recommend.services.getIndexRecommendCardList
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

    fun refresh() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getIndexRecommendCardList()
            }.let {
                recommendFeeds.postValue(it)
            }
        }

    }

    fun appendRefresh() {

    }


}