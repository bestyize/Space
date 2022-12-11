package com.thewind.spacecore.formater


/**
 * @author: read
 * @date: 2022/12/11 下午10:39
 * @description:
 */

object DateUtils {
    fun secondToMinSec(time: Int): String {
        if (time <= 0) {
            return "00:00"
        }
        val sec = if(time % 60 < 10) "0${time % 60}" else "${time % 60}"
        val min = if (time / 60 < 10) "0${time/60}" else "${time / 60}"
        return "$min:$sec"
    }
}