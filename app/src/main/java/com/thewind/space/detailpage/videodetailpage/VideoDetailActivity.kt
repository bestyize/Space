package com.thewind.space.detailpage.videodetailpage

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R
import com.thewind.space.config.router.AppRouter


@Route(path = AppRouter.PathDefine.VIDEO_DETAIL_PAGE)
class VideoDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }
}