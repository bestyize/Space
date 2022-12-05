package com.thewind.space.main.ui.videofeed.feed.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author: read
 * @date: 2022/12/4 下午6:29
 * @description:
 */
@Keep
class VideoFeedDetail {

    @SerializedName("vid")
    var vid: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("author_id")
    var authorId: String? = null

    @SerializedName("author_name")
    var authorName: String? = null

    @SerializedName("content_desc")
    var contentDesc: String? = null

    @SerializedName("share_link")
    var shareLink: String? = null

    @SerializedName("like_count")
    var likeCount: Int = 0

    @SerializedName("comment_count")
    var commentCount: Int = 0

    @SerializedName("comment_list")
    var commentList = mutableListOf<UserComment>()

}

@Keep
class VideoFeed {

    @SerializedName("code")
    var code: Int = 0
    @SerializedName("video_feed_list")
    var videoFeedList: MutableList<VideoFeedDetail> = mutableListOf()
}