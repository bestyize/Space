package com.thewind.resmanager.config

import android.os.Environment
import android.widget.Space
import com.thewind.basic.app.SpaceApp
import java.io.File

/**
 * @author: read
 * @date: 2022/12/18 下午9:18
 * @description:
 */

private var SPACE_ROOT_DIR: String? = null

fun getPublicRootDir(): String {
    return Environment.getExternalStorageDirectory().absolutePath
}

fun getSpaceRootDir(): String {
    if (SPACE_ROOT_DIR.isNullOrEmpty()) {
        SPACE_ROOT_DIR = getPublicRootDir()
    }
    return SPACE_ROOT_DIR + File.separator + "a.space" + File.separator
}

fun getSpaceMusicCacheDir(): String {
    val path = getSpaceRootDir() + "music" + File.separator + "cache" + File.separator
    if (!File(path).exists()) {
        File(path).mkdirs()
    }
    return path
}

fun getCacheDir(): String {
    return SpaceApp.application.cacheDir.absolutePath
}