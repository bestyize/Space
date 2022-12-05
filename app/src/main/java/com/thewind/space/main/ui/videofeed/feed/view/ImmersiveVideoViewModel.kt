package com.thewind.space.main.ui.videofeed.feed.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thewind.space.main.ui.videofeed.feed.model.VideoFeedDetail
import com.thewind.space.main.ui.videofeed.feed.services.ImmersiveFeedRequestHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2022/12/4 下午9:47
 * @description:
 */
class ImmersiveVideoViewModel: ViewModel() {

    val mVideoFeedList = MutableLiveData<List<VideoFeedDetail>>()

    fun update() {
        MainScope().launch {
            withContext(Dispatchers.IO){
                ImmersiveFeedRequestHelper.getImmersiveFeedFromServer()
            }.run {
                mVideoFeedList.postValue(this)
            }
        }

    }

}