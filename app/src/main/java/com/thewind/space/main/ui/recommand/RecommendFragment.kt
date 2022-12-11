package com.thewind.space.main.ui.recommand

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thewind.space.databinding.FragmentRecommendBinding
import com.thewind.spacecore.uiutil.ViewUtils


/**
 * A simple [Fragment] subclass.
 * Use the [RecommendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecommendFragment : Fragment() {


    private lateinit var binding: FragmentRecommendBinding

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
        binding.csvSearch.applyColorTheme(Color.BLUE, Color.WHITE)
    }

    override fun onResume() {
        super.onResume()
        ViewUtils.enterFullScreenMode(activity, false)
    }


}