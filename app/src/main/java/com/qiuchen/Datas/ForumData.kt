package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.ForumModel
import com.qiuchen.Views.ForumView

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午3:11.
 * @since
 */
class ForumData : BasePresenter<ForumView, ForumModel>() {
    override fun createModel(): ForumModel? {
        return ForumModel()
    }
}