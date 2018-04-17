package com.qiuchen.CustomViews

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 禁止滑动页面VP
 */
class SuperNoScrollViewPager : ViewPager {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr)


    private var canScroll = false

    fun setCanScroll(scroll: Boolean) {
        canScroll = scroll
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return !canScroll && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return !canScroll && super.onTouchEvent(ev)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,false)//干掉滑动动画
    }


}