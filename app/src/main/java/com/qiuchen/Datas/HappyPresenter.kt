package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.HappyModel
import com.qiuchen.Views.HappyView

class HappyPresenter: BasePresenter<HappyView, HappyModel>() {
    override fun createModel(): HappyModel? {
        return HappyModel()
    }


}