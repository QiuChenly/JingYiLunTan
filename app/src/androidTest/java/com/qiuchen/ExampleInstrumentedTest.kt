package com.smartcity.qiuchenly

import android.support.test.runner.AndroidJUnit4
import org.apache.http.HttpResponse
import org.apache.http.client.CookieStore
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
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