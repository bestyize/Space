package com.thewind.space.main.ui.music.model


/**
 * @author: read
 * @date: 2022/12/9 上午12:54
 * @description:
 */

/**
 * 音质：低(aac\m4a)、普通(128mp3)、高品质（320mp3）、无损音质(flac)、极致音质(44.1khz hires)、最强音质
 * @constructor
 */
enum class MusicQuality(quality: Int) {
    LQ(0),
    PQ(1),
    HQ(2),
    SQ(3),
    EQ(4),
    ZQ(5)
}

enum class SongSrc(src: String) {
    QQ("QQ"),
    KW("KW"),
    WY("WY"),
    KG("KG"),
    MG("MG")
}