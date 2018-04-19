package com.qiuchen.Views

import com.qiuchen.Base.BaseView
import com.qiuchen.DataModel.mNewsModel

interface NewsView : BaseView {
    fun onShowPageBeClick(str: String)
    fun getDataSuccess(Str: ArrayList<mNewsModel>)
}