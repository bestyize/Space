package com.thewind.space.main.ui.music.searchpage.services

import com.thewind.space.main.ui.music.model.MusicInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author: read
 * @date: 2022/12/9 上午2:44
 * @description:
 */

interface MusicSearchService {
    @POST("api/new/search")
    fun searchMusic(
        @Query("keyword") keyword: String,
        @Query("num") num: Int,
        @Query("src") src: String
    ): Call<List<MusicInfo>?>
}