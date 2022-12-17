package com.thewind.space.main.ui.indexpage.recommend.services

import android.util.Log
import com.thewind.space.api.base.RetrofitDefault
import com.thewind.space.config.router.AppRouter
import com.thewind.space.main.ui.indexpage.model.CardAction
import com.thewind.space.main.ui.indexpage.model.RecommendCard
import com.thewind.space.main.ui.indexpage.model.RecommendCardType
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.SongSrc
import com.thewind.space.main.ui.music.model.getSingerDisplayName
import com.thewind.space.main.ui.music.searchpage.services.searchMusic
import retrofit2.Call
import retrofit2.http.POST

/**
 * @author: read
 * @date: 2022/12/17 下午2:05
 * @description:
 */

private const val TAG = "[App]IndexRecommendServices"

interface IndexRecommendServices {

    @POST("api/new/search")
    fun getIndexRecommendCardList(): Call<List<RecommendCard>>

}

fun getIndexRecommendCardList(page: Int): List<RecommendCard> {

    val res = mutableListOf<RecommendCard>()

    searchMusic("热门", 30, page, SongSrc.QQ).forEach {
        res.add(RecommendCard().apply {
            cardType = RecommendCardType.MUSIC
            title = it.songName
            subTitle = it.getSingerDisplayName()
            coverUrl = it.coverUrl
            action = CardAction().apply {
                actionUrl = AppRouter.PathDefine.MUSIC_PLAYER_PAGE
                actionData = it
            }
        })
    }
    return res
}