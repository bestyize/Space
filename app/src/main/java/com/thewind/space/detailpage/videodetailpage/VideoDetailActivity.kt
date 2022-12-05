package com.thewind.space.detailpage.videodetailpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.thewind.basic.base.BaseActivity
import com.thewind.space.R


@Route(path = "/video/detail")
class VideoDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }
}