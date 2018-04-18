package com.qiuchen.UI.Fragment

import android.graphics.Color
import com.qiuchen.Adapter.ZiYuanFragmentAdapter
import com.qiuchen.Base.BaseFragment
import com.qiuchen.Datas.SearchData
import com.qiuchen.Models.SearchModel
import com.qiuchen.R
import com.qiuchen.Views.SearchViews
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.include_tablayout.*

class SearchFragment : BaseFragment<SearchViews, SearchModel, SearchData>(), SearchViews {
    override fun getLayout(): Int {
        return R.layout.fragment_search
    }

    override fun onCreated() {
        val fragments: ArrayList<ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind> = ArrayList()
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(NewsFragment(), "最新"))
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(MenuFragment(), "求助"))
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(MenuFragment(), "精华"))
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(MenuFragment(), "关注"))

        vp_searchContent.adapter = ZiYuanFragmentAdapter(childFragmentManager, fragments)
        vp_searchContent.currentItem = 0
        vp_searchContent.offscreenPageLimit = 3

        tl_ziyuan.setupWithViewPager(vp_searchContent)
        tl_ziyuan.setSelectedTabIndicatorColor(Color.parseColor("#1b9d5e"))
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && appbar != null) appbar.setExpanded(true, false)
    }

    override fun createView(): SearchViews {
        return this
    }

    override fun createPresenter(): SearchData {
        return SearchData()
    }
}