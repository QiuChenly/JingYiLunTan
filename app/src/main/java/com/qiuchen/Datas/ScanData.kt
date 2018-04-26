package com.qiuchen.Datas

import com.qiuchen.Base.BasePresenter
import com.qiuchen.Models.ScanModel
import com.qiuchen.Views.ScanView

class ScanData : BasePresenter<ScanView, ScanModel>() {
    override fun createModel(): ScanModel? {
        return ScanModel()
    }
}