package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.SearchModel
import com.qiuchen.Views.SearchViews

class SearchData : BasePresenter<SearchViews, SearchModel>() {
    override fun createModel(): SearchModel? {
        return SearchModel()
    }


}