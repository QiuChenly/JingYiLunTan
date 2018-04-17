package com.qiuchen.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainFragmentPagerAdapter(fm: FragmentManager, mlist: List<Fragment>) : FragmentPagerAdapter(fm) {

    private var list: List<Fragment> = mlist

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}