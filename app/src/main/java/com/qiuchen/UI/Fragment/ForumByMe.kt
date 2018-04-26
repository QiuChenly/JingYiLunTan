package com.qiuchen.UI.Fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.qiuchen.Base.BaseFragment
import com.qiuchen.Datas.MeData
import com.qiuchen.Models.MeModel
import com.qiuchen.R
import com.qiuchen.UI.Activity.ScanActivity
import com.qiuchen.Views.MeView
import kotlinx.android.synthetic.main.fragment_userbyme.*

class ForumByMe : BaseFragment<MeView, MeModel, MeData>(), MeView, View.OnClickListener {

    companion object {
        const val REQUEST_CODE_ScanStart = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ScanStart) {
            when (resultCode) {
                -1 -> {
                    Toast.makeText(this.context, "用户取消了登录!", Toast.LENGTH_SHORT)
                            .show()
                }

                1 -> {

                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            iv_wechatlogin.id -> {
                val i = Intent(this.context, ScanActivity::class.java)
                startActivityForResult(i, REQUEST_CODE_ScanStart)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_userbyme
    }

    override fun createView(): MeView {
        return this
    }

    override fun createPresenter(): MeData {
        return MeData()
    }

    override fun onCreated() {
        iv_wechatlogin.setOnClickListener(this)
    }
}