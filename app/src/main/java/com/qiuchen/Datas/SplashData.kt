package com.qiuchen.Datas

import com.qiuchen.Base.BaseModel
import com.qiuchen.Base.BasePresenter
import com.qiuchen.Views.SplashView

/**
 * @author QiuChenLuoYe 2018年03月18日 18时40分 创建.
 * @since
 */
class SplashData : BasePresenter<SplashView, BaseModel>() {
    override fun createModel(): BaseModel? {
        return BaseModel()
    }
}
