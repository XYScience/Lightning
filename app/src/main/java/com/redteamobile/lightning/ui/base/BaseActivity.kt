package com.redteamobile.lightning.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author SScience
 * @description
 * @email chentushen.science@gmail.com
 * @data 2020/12/4
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        initView()
        initData()

    }

    protected abstract fun getContentView() : Int

    protected abstract fun initView()

    protected abstract fun initData()
}