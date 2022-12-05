package com.thewind.basic.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author: read
 * @date: 2022/12/4 上午12:37
 * @description:
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}