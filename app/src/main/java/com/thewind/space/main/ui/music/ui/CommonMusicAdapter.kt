package com.thewind.space.main.ui.music.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter
import com.thewind.space.databinding.MusicCommonItemLayoutBinding
import com.thewind.space.main.ui.music.model.MusicInfo
import com.thewind.space.main.ui.music.model.getSingerDisplayName

/**
 * @author: read
 * @date: 2022/12/9 上午1:09
 * @description:
 */
class CommonMusicAdapter(val musicList: MutableList<MusicInfo>) :
    RecyclerView.Adapter<MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_common_item_layout, parent, false)
        val bind = MusicCommonItemLayoutBinding.bind(view)
        return MusicViewHolder(bind)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val musicInfo = musicList[position]
        holder.binding.tvMusicTitle.text = musicInfo.songName
        holder.binding.tvQuality.text = musicInfo.quality
        holder.binding.tvAlbumName.text = musicInfo.albumName
        holder.binding.tvSingersName.text = musicInfo.getSingerDisplayName()
        holder.binding.flMusicItem.setOnClickListener {
            ARouter.getInstance().build(AppRouter.PathDefine.MUSIC_PLAYER_PAGE)
                .withObject(AppRouter.MusicPlayerDefine.MUSIC_INFO, musicInfo).navigation()
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}

class MusicViewHolder(val binding: MusicCommonItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)