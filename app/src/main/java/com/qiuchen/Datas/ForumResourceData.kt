package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.ResourceModel
import com.qiuchen.Views.ForumResourcesView

/**
 * @author QiuChenLuoYe 在 2018/3/23 上午10:37.
 * @since
 */
class ForumResourceData : BasePresenter<ForumResourcesView, ResourceModel>() {
    override fun createModel(): ResourceModel? {
        return ResourceModel()
    }

}