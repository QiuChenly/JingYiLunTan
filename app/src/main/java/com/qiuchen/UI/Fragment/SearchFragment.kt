package com.qiuchen.UI.Fragment

import com.qiuchen.Base.BaseFragment
import com.qiuchen.Datas.SearchData
import com.qiuchen.Models.SearchModel
import com.qiuchen.R
import com.qiuchen.Views.SearchViews
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment<SearchViews, SearchModel, SearchData>(), SearchViews {
    override fun getLayout(): Int {
        return R.layout.fragment_search
    }

    override fun onCreated() {

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