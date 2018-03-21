package com.qiuchen.Views

import com.qiuchen.Base.BaseView
import com.qiuchen.DataModel.mTask

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午3:11.
 * @since
 */
interface ForumView : BaseView {
    fun getList(isSucc: Boolean, arrayList: ArrayList<mTask>?, reason: String)
}