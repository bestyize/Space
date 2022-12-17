package com.thewind.space.main.ui.indexpage.recommend.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.thewind.space.databinding.FragmentIndexRecommendBinding
import com.thewind.space.main.ui.indexpage.model.RecommendCard
import com.thewind.space.main.ui.indexpage.recommend.ui.adapter.RecommendCardAdapter
import com.thewind.space.main.ui.indexpage.recommend.vm.IndexRecommendFragmentViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [IndexRecommendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IndexRecommendFragment : Fragment() {

    private lateinit var binding: FragmentIndexRecommendBinding

    private lateinit var recommendVM: IndexRecommendFragmentViewModel

    private var cardList: MutableList<RecommendCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommendVM = ViewModelProvider(this)[IndexRecommendFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIndexRecommendBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRecommendFeed.layoutManager = GridLayoutManager(context, 2)
        binding.rvRecommendFeed.adapter = RecommendCardAdapter(cardList)
        recommendVM.recommendFeeds.observe(viewLifecycleOwner) {
            cardList.clear()
            cardList.addAll(it)
            binding.rvRecommendFeed.adapter?.notifyDataSetChanged()
            binding.srlRefresh.isRefreshing = false
        }
        binding.srlRefresh.apply {
            setColorSchemeColors(Color.RED)
        }
        binding.srlRefresh.setOnRefreshListener {
            binding.srlRefresh.isRefreshing = true
            recommendVM.refresh()
        }
        recommendVM.refresh()

    }

    companion object {

        @JvmStatic
        fun newInstance() = IndexRecommendFragment()
    }
}