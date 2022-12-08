package com.thewind.space.api.base

import com.thewind.space.main.ui.videofeed.feed.services.ImmersiveFeedRequestHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: read
 * @date: 2022/12/9 上午2:51
 * @description:
 */

const val BASE_URL = "https://thewind.xyz/"

val RetrofitDefault: Retrofit by lazy { Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
    GsonConverterFactory.create())
    .build() }