package com.smartcity.qiuchenly

import android.support.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var mCS: CookieStore
    lateinit var mHttp: HttpClient

    @Test
    @Throws(Exception::class)
    fun useAppContext() {
    }
}
