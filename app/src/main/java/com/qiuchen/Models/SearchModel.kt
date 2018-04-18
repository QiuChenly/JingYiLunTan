package com.qiuchen.Models

import com.qiuchen.API.mJingYi
import com.qiuchen.Base.BaseModel
import kotlin.concurrent.thread

class SearchModel : BaseModel() {

    fun getMainPageDatas(Get: mJingYi.Companion.GetData) {
        thread {
            mJingYi.getMainPageData(Get)
        }
    }

}