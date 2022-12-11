package com.thewind.space.main.ui.music.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @author: read
 * @date: 2022/12/9 上午12:52
 * @description:
 */
@Keep
class MusicInfo: java.io.Serializable {
    var songSrc: String? = null
    var songId: String? = null
    var songName: String? = null
    var singersName: List<String>? = null
    var albumId: String? = null
    var albumName: String? = null

    @SerializedName("picUrl")
    var coverUrl: String? = null

    @SerializedName("lrcUrl")
    var lyricUrl: String? = null
    var mvUrl: String? = null
    var songLink: String? = null
    var quality: String = MusicQuality.LQ.name
    var downloadLinkMap: Map<String, String>? = null

}

fun MusicInfo?.getSingerDisplayName(): String {
    this ?: return ""
    singersName?.let {
        return when(it.size) {
            0 -> ""
            1 -> it[0]
            else -> {
                it[0] + "," + it[1]
            }
        }
    }
    return ""
}