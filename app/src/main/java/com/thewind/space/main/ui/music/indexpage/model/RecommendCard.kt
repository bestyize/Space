package com.thewind.space.main.ui.music.indexpage.model

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
    var action: CardAction? = null
}

@Keep
class CardAction {
    var actionUrl: String? = null
    var actionData: Any? = null
}

@Keep
enum class RecommendCardType(val type: Int) {
    NONE(0),
    MUSIC(1),
    SHORT_VIDEO(2),
    LONG_VIDEO(3)
}