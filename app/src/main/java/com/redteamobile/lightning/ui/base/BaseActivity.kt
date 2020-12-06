package com.redteamobile.lightning.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.redteamobile.lightning.util.UIUtil

/**
 * @author SScience
 * @description
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UIUtil.setNavigationBarIconColor(this, true)
        UIUtil.setStatusBarBackground(this, android.R.color.white)
        setContentView(getContentView())
        initView()
        initData()

    }

    protected abstract fun getContentView() : Int

    protected abstract fun initView()

    protected abstract fun initData()
}