package com.qiuchen.UI.Fragment

import com.qiuchen.Base.BaseFragment
import com.qiuchen.Datas.HappyPresenter
import com.qiuchen.Models.HappyModel
import com.qiuchen.R
import com.qiuchen.UI.Activity.MainActivityEx
import com.qiuchen.Views.HappyView
import kotlinx.android.synthetic.main.fragment_happy.*

class HappyFragment : BaseFragment<HappyView, HappyModel, HappyPresenter>(), HappyView {
    override fun getLayout(): Int {
        return R.layout.fragment_happy
    }

    override fun onCreated() {
        val a = activity as MainActivityEx
        a.setSupportActionBar(tb_hpToolBar)
        a.apply {
            title = ""
        }
        a.supportActionBar!!.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun createView(): HappyView {
        return this
    }

    override fun createPresenter(): HappyPresenter {
        return HappyPresenter()
    }
}