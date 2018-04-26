package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.MeModel
import com.qiuchen.Views.MeView

class MeData : BasePresenter<MeView, MeModel>() {
    override fun createModel(): MeModel? {
        return MeModel()
    }
}