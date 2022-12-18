package com.thewind.space.main.ui.music.indexpage.recommend.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thewind.space.main.ui.music.indexpage.recommend.ui.MusicRecommendFragment


/**
 * @author: read
 * @date: 2022/12/18 下午4:54
 * @description:
 */


class IndexFragmentPagerAdapter(
    private var list: List<String>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return MusicRecommendFragment.newInstance(list[position])
    }
}