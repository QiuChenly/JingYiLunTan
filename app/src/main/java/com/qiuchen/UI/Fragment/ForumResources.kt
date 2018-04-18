package com.qiuchen.UI.Fragment

import android.graphics.Color
import com.qiuchen.Adapter.ZiYuanFragmentAdapter
import com.qiuchen.Base.BaseFragment
import com.qiuchen.Datas.ForumResourceData
import com.qiuchen.Models.ResourceModel
import com.qiuchen.R
import com.qiuchen.Views.ForumResourcesView
import kotlinx.android.synthetic.main.fragment_forum.*

/**
 * @author QiuChenLuoYe 在 2018/3/23 上午10:31.
 * @since
 */
class ForumResources : BaseFragment<ForumResourcesView, ResourceModel, ForumResourceData>(), ForumResourcesView {

    override fun getLayout(): Int {
        return R.layout.fragment_forum
    }

    override fun createView(): ForumResourcesView {
        return this
    }

    override fun createPresenter(): ForumResourceData {
        return ForumResourceData()
    }

    override fun onCreated() {
        val fragments: ArrayList<ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind> = ArrayList()
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(MenuFragment(), "标题1"))
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(MenuFragment(), "标题2"))
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(MenuFragment(), "标题3"))
        fragments.add(ZiYuanFragmentAdapter.ZiYuanFragmentAdapter_Kind(MenuFragment(), "标题4"))

        myViewPager.adapter = ZiYuanFragmentAdapter(childFragmentManager, fragments)
        myViewPager.currentItem = 0
        myViewPager.offscreenPageLimit = 3

        tl_ziyuan.setupWithViewPager(myViewPager)
        tl_ziyuan.setSelectedTabIndicatorColor(Color.parseColor("#1b9d5e"))
    }
}