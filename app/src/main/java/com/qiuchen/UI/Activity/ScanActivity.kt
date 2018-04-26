package com.qiuchen.UI.Activity

import android.view.View
import com.qiuchen.Base.BaseActivity
import com.qiuchen.Datas.ScanData
import com.qiuchen.Models.ScanModel
import com.qiuchen.R
import com.qiuchen.Views.ScanView
import kotlinx.android.synthetic.main.activity_scan.*

class ScanActivity : BaseActivity<ScanView, ScanModel, ScanData>(), ScanView, View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0?.id) {
            fl_backuppage.id -> {
                setResult(-1)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_scan
    }

    override fun createView(): ScanView {
        return this
    }

    override fun createPresenter(): ScanData {
        return ScanData()
    }

    override fun onCreated() {
        fl_backuppage.setOnClickListener(this)
    }
}
