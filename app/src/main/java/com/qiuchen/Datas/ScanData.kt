package com.qiuchen.Datas

import android.os.Handler
import android.os.Looper
import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.ScanModel
import com.qiuchen.Views.ScanView

class ScanData : BasePresenter<ScanView, ScanModel>() {

    val hand = Handler(Looper.getMainLooper())
    override fun createModel(): ScanModel? {
        return ScanModel()
    }

    fun getQRCode() {

    }

    val mThread = object : Thread() {

        var continueRun = true

        fun closed() {
            continueRun = false
        }

        override fun run() {
            super.run()
            while (continueRun && getView() != null) {

            }
        }
    }
}

