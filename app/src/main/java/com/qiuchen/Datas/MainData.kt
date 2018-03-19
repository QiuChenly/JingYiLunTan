package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.MainModel
import com.qiuchen.Views.MainView

/**
 * @author QiuChenLuoYe 2018年03月18日 20时15分 创建.
 * @since
 */
class MainData : BasePresenter<MainView, MainModel>() {
    override fun createModel(): MainModel? {
        return MainModel()
    }


}
