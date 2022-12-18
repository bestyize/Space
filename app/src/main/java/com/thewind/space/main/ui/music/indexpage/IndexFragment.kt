package com.thewind.space.main.ui.music.indexpage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayoutMediator
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter
import com.thewind.space.databinding.FragmentRecommendBinding
import com.thewind.space.main.ui.music.indexpage.recommend.ui.adapter.IndexFragmentPagerAdapter
import com.thewind.space.main.ui.music.indexpage.recommend.vm.IndexFragmentViewModel
import com.thewind.space.main.ui.music.searchpage.ui.MusicSearchFragment
import com.thewind.space.main.ui.music.searchpage.ui.searchbar.SearchBarViewListener
import com.thewind.spacecore.uiutil.ViewUtils


/**
 * A simple [Fragment] subclass.
 * Use the [IndexFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IndexFragment : Fragment() {


    private lateinit var binding: FragmentRecommendBinding
    private lateinit var indexFragmentViewModel: IndexFragmentViewModel
    private var tabs = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        indexFragmentViewModel = ViewModelProvider(this)[IndexFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecommendBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.csvSearch.abandonInput()
        binding.csvSearch.applyHintText("搜你所想")
        binding.csvSearch.applyColorTheme(Color.WHITE, Color.BLACK)
        binding.vp2Container.adapter =
            IndexFragmentPagerAdapter(tabs, childFragmentManager, lifecycle)
        binding.vp2Container.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.vp2Container.isSaveEnabled = false
        indexFragmentViewModel.tabs.observe(viewLifecycleOwner) {
            tabs.clear()
            tabs.addAll(it)
            binding.vp2Container.adapter?.notifyDataSetChanged()
            TabLayoutMediator(binding.tlIndex, binding.vp2Container, false) { tab, pos ->
                tab.text = tabs[pos]
            }.attach()
        }
        indexFragmentViewModel.requestTabs()


        binding.csvSearch.apply {
            searchListener = object : SearchBarViewListener {
                override fun onSearchBarClick() {
                    ARouter.getInstance().build(AppRouter.PathDefine.MUSIC_SEARCH_PAGE).navigation()
                }

                override fun onSearchClick(text: String) {
                    ARouter.getInstance().build(AppRouter.PathDefine.MUSIC_SEARCH_PAGE).navigation()
                }

                override fun onInputClick() {

                }

                override fun onBackClick() {

                }

                override fun onTextChanged(text: String) {

                }
            }
            setOnClickListener {
                ARouter.getInstance().build(AppRouter.PathDefine.MUSIC_SEARCH_PAGE).navigation()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        ViewUtils.enterFullScreenMode(activity, true)
    }


}