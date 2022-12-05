package com.thewind.spacecore.extension

/**
 * @author: read
 * @date: 2022/12/6 上午3:46
 * @description:
 */

fun Int.atLast(minVal: Int): Boolean {
    return this >= minVal
}

fun Int.atMost(maxVal: Int): Boolean {
    return this <= maxVal
}

fun Float.atLast(minVal: Float): Boolean {
    return this >= minVal
}

fun Float.atMost(maxVal: Float): Boolean {
    return this <= maxVal
}

fun Long.atLast(minVal: Long): Boolean {
    return this >= minVal
}

fun Long.atMost(maxVal: Long): Boolean {
    return this <= maxVal
}

fun Double.atLast(minVal: Double): Boolean {
    return this >= minVal
}

fun Double.atMost(maxVal: Double): Boolean {
    return this <= maxVal
}