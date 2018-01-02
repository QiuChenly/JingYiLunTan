package com.qiuchen

import com.qiuchen.API.mJingYi
import com.qiuchen.DataModel.mTask
import com.qiuchen.Utils.mUtils
import com.qiuchen.httpClient.httpClient
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : mJingYi.TaskCallBack {
    override fun taskCallback(a: List<mTask>) {

    }

    @Test
    fun addition_isCorrect() {
        mJingYi.getTasklist(this)

    }
}
