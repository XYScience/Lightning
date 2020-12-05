package com.redteamobile.lightning.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @author SScience
 * @description
 */
class MainViewPager(context: Context, attributeSet: AttributeSet) : ViewPager(context,attributeSet){

    private var isScroll = true

    public fun setScroll(isScroll:Boolean){
        this.isScroll=isScroll
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isScroll && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isScroll && super.onTouchEvent(ev)

    }

}