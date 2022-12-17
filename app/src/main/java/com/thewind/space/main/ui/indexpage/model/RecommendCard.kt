package com.thewind.space.main.ui.indexpage.model

import androidx.annotation.Keep

/**
 * @author: read
 * @date: 2022/12/17 上午1:46
 * @description:
 */
@Keep
class RecommendCard {
    var title: String? = null
    var subTitle: String? = null
    var coverUrl: String? = null
    var cardType: RecommendCardType = RecommendCardType.NONE
    var url: String? = null
}

@Keep
enum class RecommendCardType(val type: Int) {
    NONE(0),
    MUSIC(1),
    SHORT_VIDEO(2),
    LONG_VIDEO(3)
}