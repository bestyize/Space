package com.thewind.space.main.ui.music.indexpage.recommend.ui.adapter

import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.space.config.router.AppRouter
import com.thewind.space.main.ui.music.indexpage.model.RecommendCard
import com.thewind.space.main.ui.music.indexpage.model.RecommendCardType
import com.thewind.space.main.ui.music.model.MusicInfo

/**
 * @author: read
 * @date: 2022/12/18 上午3:21
 * @description:
 */
object CardActionHandler {
    fun doAction(recommendCard: RecommendCard) {
        when(recommendCard.cardType) {
            RecommendCardType.MUSIC -> {
                val data = recommendCard.action?.actionData
                if (data is MusicInfo) {
                    ARouter.getInstance().build(AppRouter.PathDefine.MUSIC_PLAYER_PAGE)
                        .withObject(AppRouter.MusicPlayerDefine.MUSIC_INFO, data).navigation()
                }
            }
            else ->{}
        }
    }
}