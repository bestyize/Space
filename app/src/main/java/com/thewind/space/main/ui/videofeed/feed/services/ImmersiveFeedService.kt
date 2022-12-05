package com.thewind.space.main.ui.videofeed.feed.services

import com.thewind.space.main.ui.videofeed.feed.model.VideoFeed
import retrofit2.Call
import retrofit2.http.POST

/**
 * @author: read
 * @date: 2022/12/4 下午6:28
 * @description:
 */

interface ImmersiveFeedService {

    @POST("/api/immersive/video/feeds")
    fun getVideoFeeds(): Call<VideoFeed>
}