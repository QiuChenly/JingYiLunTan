package com.qiuchen.UI.Fragment

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
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
    override fun getCtx(): Context {
        return this.context!!
    }

    override fun onClick(position: Int, title: String) {
        (activity as MainActivityEx).switchViews(position, title)
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
            title = "论坛资源"
            this.icon = R.drawable.ic_feedback_black_24dp
        }, MenuBean().apply {
            title = "论坛接单"
            this.icon = R.drawable.ic_monetization_on_black_24dp
        }, MenuBean().apply {
            title = "软件出售"
            this.icon = R.drawable.ic_sentiment_very_dissatisfied_black_24dp
        })
        lv_menu.layoutManager = LinearLayoutManager(this.context)
        lv_menu.itemAnimator = DefaultItemAnimator()
        lv_menu.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                outRect?.bottom = 2
            }
        })
        lv_menu.adapter = MenuAdapter(list, this)
    }
}
