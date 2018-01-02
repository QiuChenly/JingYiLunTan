package com.qiuchen

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.qiuchen.API.mJingYi
import com.qiuchen.DataModel.mTask

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : mJingYi.TaskCallBack {
    override fun taskCallback(a: List<mTask>) {

    }

    @Test
    fun useAppContext() {
        mJingYi.Companion.getTasklist(this)
    }
}
