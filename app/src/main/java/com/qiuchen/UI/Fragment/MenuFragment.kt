package com.qiuchen.UI.Fragment

import com.qiuchen.Adapter.MenuAdapter
import com.qiuchen.Base.BaseFragment
import com.qiuchen.Base.BaseModel
import com.qiuchen.Base.BasePresenter
import com.qiuchen.Base.BaseView
import com.qiuchen.Beans.MenuBean
import com.qiuchen.R
import com.qiuchen.UI.Activity.MainActivityEx
import kotlinx.android.synthetic.main.layout_menu.*

/**
 * @author QiuChenLuoYe 2018年03月18日 23时49分 创建.
 * @since
 */
class MenuFragment : BaseFragment<BaseView, BaseModel, MenuFragment.thisPres>(), BaseView, MenuAdapter.MenuItemCallback {
    override fun onClick(position: Int) {
        (activity as MainActivityEx).switchViews(position)
    }

    override fun getLayout(): Int {
        return R.layout.layout_menu
    }

    override fun createView(): BaseView {
        return this
    }

    override fun createPresenter(): MenuFragment.thisPres {
        return thisPres()
    }

    class thisPres : BasePresenter<BaseView, BaseModel>() {
        override fun createModel(): BaseModel? {
            return BaseModel()
        }
    }

    override fun onCreated() {
        val list = arrayListOf<MenuBean>(MenuBean().apply {
            title = "论坛接单"
            this.icon = R.drawable.ic_monetization_on_black_24dp
        }, MenuBean().apply {
            title = "随机精华"
            this.icon = R.drawable.ic_feedback_black_24dp
        }, MenuBean().apply {
            title = "最新求助"
            this.icon = R.drawable.ic_sentiment_very_dissatisfied_black_24dp
        })
        lv_menu.adapter = MenuAdapter(list, this)
    }
}
