package com.thewind.spacecore.formater

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

/**
 * @author: read
 * @date: 2022/12/18 上午2:50
 * @description:
 */

private val GSON = GsonBuilder().disableHtmlEscaping().create()
private const val TAG = "[SpaceCore]Json"
object JSON {

    @JvmStatic
    fun toJson(obj: Any?): String {
        obj ?: return ""
        try {
            return GSON.toJson(obj)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "toJson, error, msg = $e")
        }
        return ""
    }

    @JvmStatic
    fun <T> fromJson(str: String?, clazz: Class<T>): T {
        return GSON.fromJson(str, clazz)
    }

    @JvmStatic
    fun <T> fromJson(str: String?, clazz: Type): T {
        return GSON.fromJson(str, clazz)
    }
}