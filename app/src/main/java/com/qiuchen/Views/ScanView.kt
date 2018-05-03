package com.qiuchen.Views

import com.qiuchen.Base.BaseView

interface ScanView : BaseView {
    fun getImage(state: Int, Url: String)
}