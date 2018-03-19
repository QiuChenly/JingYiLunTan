package com.qiuchen.UI.Fragment

import com.qiuchen.Base.BaseFragment
import com.qiuchen.Datas.ForumData
import com.qiuchen.Models.ForumModel
import com.qiuchen.R
import com.qiuchen.Views.ForumView

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午3:10.
 * @since
 */
class ForumOrderFragment : BaseFragment<ForumView, ForumModel, ForumData>(), ForumView {
    override fun getLayout(): Int {
        return R.layout.activity_show_list
    }

    override fun onCreated() {

    }

    override fun createView(): ForumView {
        return this
    }

    override fun createPresenter(): ForumData {
        return ForumData()
    }
}