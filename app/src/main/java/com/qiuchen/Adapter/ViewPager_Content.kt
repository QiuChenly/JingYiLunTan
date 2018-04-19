package com.qiuchen.Adapter

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.qiuchen.R

/**
 * Created by qiuchen on 2018/1/1.
 */
class ViewPager_Content(var mList: List<String>,
                        var mViewEvent: ViewEvent) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.image_show_pager, container, false)
        val imageView = view.findViewById(R.id.iv_showImage) as ImageView
        Glide.with(container.context).load(mList[position]).into(imageView)
        view.tag = position
        view.setOnClickListener {
            mViewEvent.ViewPagerViewEvent(view, position)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    interface ViewEvent {
        fun ViewPagerViewEvent(view: View, position: Int)
    }
}
