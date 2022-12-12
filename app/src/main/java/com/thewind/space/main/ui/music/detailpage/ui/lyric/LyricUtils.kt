package com.thewind.space.main.ui.music.detailpage.ui.lyric

import com.thewind.spacecore.formater.DateUtils

/**
 * @author: read
 * @date: 2022/12/12 上午12:05
 * @description:
 * [00:04.00]作曲：李偲菘
 */
object LyricUtils {

    fun parseLyric(lyricStr: String): List<LyricInfo> {
        val list = mutableListOf<LyricInfo>()
        lyricStr.split("\n").toList().forEach { pair ->
            if (pair.length > 9 && pair[0] == '[' && pair[9] == ']') {
                list.add(LyricInfo().apply {
                    timeSecond = DateUtils.hourMinSecToSec(pair.substring(1, 9))
                    lyric = pair.substringAfter(']')
                })
            }
        }
        return list.filter { !it.lyric.isNullOrBlank() }
    }
}