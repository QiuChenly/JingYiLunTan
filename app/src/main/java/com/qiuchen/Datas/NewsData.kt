package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.NewsModel
import com.qiuchen.Views.NewsView

class NewsData: BasePresenter<NewsView, NewsModel>() {
    override fun createModel(): NewsModel? {
        return NewsModel()
    }


}