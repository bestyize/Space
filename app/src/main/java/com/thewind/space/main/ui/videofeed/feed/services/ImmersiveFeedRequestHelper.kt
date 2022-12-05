package com.thewind.space.main.ui.videofeed.feed.services

import com.thewind.space.main.ui.videofeed.feed.model.VideoFeedDetail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: read
 * @date: 2022/12/4 下午9:59
 * @description:
 */
object ImmersiveFeedRequestHelper {

    private const val BASE_URL = "https://thewind.xyz"

    fun getImmersiveFeedFromServer(): List<VideoFeedDetail> {
        //return defaultRetrofit().create(ImmersiveFeedService::class.java).getVideoFeeds().execute().body()?.videoFeedList ?: emptyList()

        return listOf(
            VideoFeedDetail().apply {
                url =
                    "http://www.huoying666.com/video/border4.mp4"
            },
            VideoFeedDetail().apply {
                url =
                    "https://oss.zhiyu.art/sucai/preview/c20f7827a0279d78742c1d70c584f616a.mp4"
            },
            VideoFeedDetail().apply {
                url =
                    "https://oss.zhiyu.art/sucai/preview/209c0ec0e8882cec3cecdb88e81bf926a.mp4"
            },
            VideoFeedDetail().apply {
                url =
                    "https://oss.zhiyu.art/sucai/preview/9d5b5c3d8b4162711cd1f0f0a4a9de1ba.mp4"
            }, VideoFeedDetail().apply {
                url = "https://oss.zhiyu.art/sucai/preview/a3dfd8c84c6ddfc13f2aa88fafeeb1aaa.mp4"
            },
            VideoFeedDetail().apply {
                url = "https://oss.zhiyu.art/sucai/preview/627e4a7a52b7fda6479bcae68b01ed52a.mp4"
            },
            VideoFeedDetail().apply {
                url = "https://oss.zhiyu.art/sucai/preview/2e868232fae1e22fba13a1826f6fe6f1a.mp4"
            }, VideoFeedDetail().apply
            { url = "https://cdn.coverr.co/videos/coverr-the-ocean-in-the-evening-3606/1080p.mp4" })
    }

    private fun defaultRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
}