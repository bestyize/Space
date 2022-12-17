package com.thewind.space.main.ui.indexpage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thewind.space.R
import com.thewind.space.databinding.FragmentRecommendBinding
import com.thewind.space.main.ui.indexpage.recommend.ui.IndexRecommendFragment
import com.thewind.spacecore.uiutil.ViewUtils


/**
 * A simple [Fragment] subclass.
 * Use the [IndexFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IndexFragment : Fragment() {


    private lateinit var binding: FragmentRecommendBinding
    private lateinit var recommendFragment: IndexRecommendFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recommendFragment = IndexRecommendFragment.newInstance()
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fcv_container, recommendFragment)
            ?.commitAllowingStateLoss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecommendBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.csvSearch.applyHintText("搜你所想")
        binding.csvSearch.applyColorTheme(Color.WHITE, Color.BLACK)
    }

    override fun onResume() {
        super.onResume()
        ViewUtils.enterFullScreenMode(activity, true)
    }


}