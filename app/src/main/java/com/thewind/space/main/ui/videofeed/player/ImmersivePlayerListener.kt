package com.thewind.space.main.ui.videofeed.player

/**
 * @author: read
 * @date: 2022/12/6 上午2:32
 * @description:
 */
interface ImmersivePlayerOperationListener {

    fun onLikeClicked()

    fun onCommentClicked()

    fun onCollectClicked()

    fun onShareClicked()

    fun onCoverClicked()
}

interface ImmersivePlayerStateListener{
    fun onStateChanged(state: PlayerState)
}

interface ImmersivePlayerUpdateListener{
    fun onProgressUpdate(currPos: Int)
}
