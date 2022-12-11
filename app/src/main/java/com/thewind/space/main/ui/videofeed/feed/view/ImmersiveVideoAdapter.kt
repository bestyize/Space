package com.thewind.space.main.ui.videofeed.feed.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thewind.space.R
import com.thewind.space.main.ui.videofeed.feed.model.VideoFeedDetail
import com.thewind.space.main.ui.videofeed.player.ImmersivePlayer
import com.thewind.space.main.ui.videofeed.player.ImmersivePlayerOperationListener
import com.thewind.space.main.ui.videofeed.player.ImmersivePlayerStateListener
import com.thewind.space.main.ui.videofeed.player.PlayerState

/**
 * @author: read
 * @date: 2022/12/4 下午9:39
 * @description:
 */
class ImmersiveVideoAdapter(private val feedList: MutableList<VideoFeedDetail>): RecyclerView.Adapter<ImmersiveVideoAdapter.ImmersiveVideoViewHolder>() {

    inner class ImmersiveVideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val immersivePlayer: ImmersivePlayer = view.findViewById(R.id.ipv_video_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImmersiveVideoViewHolder {
        val viewRoot = LayoutInflater.from(parent.context).inflate(R.layout.immersive_video_layout, parent, false)
        return ImmersiveVideoViewHolder(viewRoot)
    }

    override fun onBindViewHolder(holder: ImmersiveVideoViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder, position = $position, holder = ${holder.hashCode()}")
        holder.immersivePlayer.apply {
            playUrl = feedList[position].url
            operationListener = object : ImmersivePlayerOperationListener {
                override fun onLikeClicked() {
                    Log.i(TAG, "onLikeClicked")
                }

                override fun onCommentClicked() {
                    Log.i(TAG, "onCommentClicked")
                }

                override fun onCollectClicked() {
                    Log.i(TAG, "onCollectClicked")
                }

                override fun onShareClicked() {
                    Log.i(TAG, "onShareClicked")
                }

                override fun onCoverClicked() {
                    Log.i(TAG, "onVideoClicked")
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ImmersiveVideoViewHolder) {
        Log.i(TAG, "onViewAttachedToWindow, holder = ${holder.hashCode()}")
        super.onViewAttachedToWindow(holder)
        holder.immersivePlayer.prepare()
    }

    override fun onViewDetachedFromWindow(holder: ImmersiveVideoViewHolder) {
        Log.i(TAG, "onViewDetachedFromWindow, holder = ${holder.hashCode()}")
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: ImmersiveVideoViewHolder) {
        Log.i(TAG, "onViewRecycled, holder = ${holder.hashCode()}")
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return feedList.size
    }


    companion object {
        private const val TAG = "[App]ImmersiveVideoAdapter"
    }

}