package com.qiuchen.UI.Fragment

import com.qiuchen.Base.BaseFragment
import com.qiuchen.Datas.HappyPresenter
import com.qiuchen.Models.HappyModel
import com.qiuchen.R
import com.qiuchen.Views.HappyView

class HappyFragment : BaseFragment<HappyView, HappyModel, HappyPresenter>(), HappyView {
    override fun getLayout(): Int {
        return R.layout.fragment_happy
    }

    override fun onCreated() {

    }

    override fun createView(): HappyView {
        return this
    }

    override fun createPresenter(): HappyPresenter {
        return HappyPresenter()
    }
}