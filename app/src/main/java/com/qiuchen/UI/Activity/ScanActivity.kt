package com.qiuchen.UI.Activity

import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.qiuchen.Base.BaseActivity
import com.qiuchen.Datas.ScanData
import com.qiuchen.Models.ScanModel
import com.qiuchen.R
import com.qiuchen.Views.ScanView
import kotlinx.android.synthetic.main.activity_scan.*

class ScanActivity : BaseActivity<ScanView, ScanModel, ScanData>(), ScanView, View.OnClickListener {
    override fun getImage(state: Int, Url: String) {
        if (state != 0) {
            Toast.makeText(this, "获取二维码失败！", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    val state = -1

    override fun onClick(p0: View?) {
        when (p0?.id) {
            fl_backuppage.id -> {
                setResult(state)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_scan
    }

    override fun createView(): ScanView {
        return this
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) setResult(state)
        return super.onKeyUp(keyCode, event)
    }

    override fun createPresenter(): ScanData {
        return ScanData()
    }

    override fun onCreated() {
        fl_backuppage.setOnClickListener(this)
        getPres()?.getQRCode()
    }
}
