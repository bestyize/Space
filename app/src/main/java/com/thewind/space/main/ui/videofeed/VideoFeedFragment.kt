package com.thewind.space.main.ui.videofeed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thewind.space.R
import com.thewind.space.main.ui.videofeed.feed.view.ImmersivePageHelper
import com.thewind.space.main.ui.videofeed.feed.view.ImmersiveVideoAdapter
import com.thewind.space.main.ui.videofeed.feed.view.ImmersiveVideoViewModel
import com.thewind.spacecore.uiutil.ViewUtils

/**
 * A simple [Fragment] subclass.
 * Use the [VideoFeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoFeedFragment : Fragment() {

    private val mFeedVideoVM = ImmersiveVideoViewModel()
    private lateinit var mRecyclerView: RecyclerView

    override fun onResume() {
        super.onResume()
        ViewUtils.enterImmersiveFullScreenMode(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView, this = ${this.hashCode()}")
        val view = inflater.inflate(R.layout.fragment_video_feed, container, false)
        init(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFeedVideoVM.update()

    }

    private fun init(view: View) {
        mRecyclerView = view.findViewById(R.id.rv_video_feed)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addOnScrollListener(ImmersivePageHelper(mRecyclerView))
        mFeedVideoVM.mVideoFeedList.observe(viewLifecycleOwner) {
            mRecyclerView.adapter = ImmersiveVideoAdapter(it.toMutableList())
        }
    }

    override fun onStop() {
        super.onStop()
        ViewUtils.exitFullScreenMode(activity, true)
    }


    companion object {
        private const val TAG = "[App]VideoFeedFragment"
    }

}