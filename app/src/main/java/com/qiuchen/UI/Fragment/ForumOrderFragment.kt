package com.qiuchen.UI.Fragment

import android.graphics.Rect
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchen.Adapter.DanZiAdapter
import com.qiuchen.Base.BaseFragment
import com.qiuchen.DataModel.mTask
import com.qiuchen.Datas.ForumData
import com.qiuchen.Models.ForumModel
import com.qiuchen.R
import com.qiuchen.Views.ForumView
import kotlinx.android.synthetic.main.activity_show_list.*

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午3:10.
 * @since
 */
class ForumOrderFragment : BaseFragment<ForumView, ForumModel, ForumData>(), ForumView, SwipeRefreshLayout.OnRefreshListener, DanZiAdapter.onItemClick {

    override fun onTaskItemBeClick(position: Int, view: View) {
    }

    override fun getList(isSucc: Boolean, arrayList: ArrayList<mTask>?, reason: String) {
        println(isSucc)
        if (isSucc)
            rv_Adapter?.addData(arrayList!!, mTaskListRV)
        else
            show(reason)
        mSwipeRefresh.isRefreshing = false
    }

    override fun onRefresh() {
        getPres()?.getList()
    }

    override fun getLayout(): Int {
        return R.layout.activity_show_list
    }

    override fun createView(): ForumView {
        return this
    }

    override fun createPresenter(): ForumData {
        return ForumData()
    }

    private var rv_Adapter: DanZiAdapter? = null

    override fun onCreated() {
        mSwipeRefresh.setOnRefreshListener(this)
        mTaskListRV.layoutManager = LinearLayoutManager(this.context)
        mTaskListRV.itemAnimator = DefaultItemAnimator()
        mTaskListRV.setHasFixedSize(false)
        mTaskListRV.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                outRect?.bottom = 5
            }
        })
        rv_Adapter = DanZiAdapter(ArrayList(), this, this.context!!)
        mTaskListRV.adapter = rv_Adapter

        //开启后台线程刷新
        getPres()?.hand()
    }
}