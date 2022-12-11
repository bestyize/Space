package com.thewind.space.main.ui.music.detailpage.services

import com.thewind.space.main.ui.music.model.MusicPlayInfo
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author: read
 * @date: 2022/12/11 下午9:16
 * @description:
 */
interface MusicPlayUrlService {

    @POST("api/new/player")
    fun getPlayUrl(@Query("shareId") shardId: String?): Call<List<MusicPlayInfo>>?
}