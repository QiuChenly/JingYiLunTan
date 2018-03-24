package com.qiuchen.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * @author QiuChenLuoYe 在 2018/3/23 上午10:19.
 * @since
 */
class ZiYuanFragmentAdapter(fm: FragmentManager,
                            val mZiYuanFragmentAdapter_Kind: ArrayList<ZiYuanFragmentAdapter_Kind>) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return mZiYuanFragmentAdapter_Kind[position].fragment
    }

    override fun getCount(): Int {
        return mZiYuanFragmentAdapter_Kind.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mZiYuanFragmentAdapter_Kind[position].title
    }

    class ZiYuanFragmentAdapter_Kind(var fragment: Fragment, var title: String)
}