package com.qiuchen.Datas

import com.qiuchen.API.mJingYi
import com.qiuchen.Base.BasePresenter
import com.qiuchen.DataModel.mNewsModel
import com.qiuchen.Models.NewsModel
import com.qiuchen.Views.NewsView

class NewsData: BasePresenter<NewsView, NewsModel>() {
    override fun createModel(): NewsModel? {
        return NewsModel()
    }
    fun getData() {
        getModel()?.getMainPageDatas(object : mJingYi.Companion.GetData {
            override fun getDataSuccess(str: ArrayList<mNewsModel>) {
                if (getView() != null) {
                    getView()?.getDataSuccess(str)
                }
            }
        })
    }
}