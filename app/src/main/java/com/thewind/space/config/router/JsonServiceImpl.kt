@file:JvmName(name = "JsonServiceImpl")
@file:JvmMultifileClass
package com.thewind.space.config.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.thewind.spacecore.formater.JSON.fromJson
import com.thewind.spacecore.formater.JSON.toJson
import java.lang.reflect.Type

/**
 * @author: read
 * @date: 2022/12/18 上午3:01
 * @description:
 */
@Route(path = "/yourservicegroupname/json")
open class JsonServiceImpl : SerializationService {
    override fun <T> json2Object(input: String?, clazz: Class<T>): T {
        return fromJson(input, clazz)
    }

    override fun object2Json(instance: Any?): String {
        return toJson(instance)
    }

    override fun <T> parseObject(input: String?, clazz: Type): T {
        return fromJson(input, clazz)
    }

    override fun init(context: Context?) {}
}