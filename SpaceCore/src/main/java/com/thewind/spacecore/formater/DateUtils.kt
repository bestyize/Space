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
    // 00:11:22
    fun hourMinSecToSec(time: String?): Int {
        time ?: return 0
        if (time.length < 5) {
            return 0
        }
        return time.subSequence(0, 2).toInt() * 60 + time.subSequence(3, 5).toInt()
    }

    private fun CharSequence?.toInt(): Int {
        this ?: return 0
        var pos = 0
        var res = 0
        while (pos < this.length ) {
            res = res * 10 + (this[pos++] - '0')
        }
        return res
    }


}

