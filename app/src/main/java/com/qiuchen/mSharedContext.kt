package com.qiuchen

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley.newRequestQueue
import com.qiuchen.Utils.mDataBaseHelper
import com.qiuchen.Utils.mUtils
import com.qiuchen.jingyi.nativeHttp.nHttp
import com.squareup.leakcanary.LeakCanary


/**
 * @author QiuChenLuoYe 在 2018/1/1 下午9:40.
 * @since
 */
class mSharedContext : Application() {
    companion object {
        private lateinit var CookieEx: nHttp.nCookie
        private lateinit var mSP: SharedPreferences
        private lateinit var mQueue: RequestQueue

        var mLoginState = com.qiuchen.DataModel.mLoginState()

        fun getSP(): SharedPreferences {
            return mSP
        }

        fun saveCookie() {
            mSP.edit().putString("Cookie", CookieEx.toString())
                    .apply()
        }

        fun getQueue() = mQueue

        fun getCookie(): nHttp.nCookie {
            return CookieEx
        }

        private val MDATABASE_NAME = "mDB.db"
    }

    override fun onCreate() {
        super.onCreate()
        if(LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
        mUtils.mDataBaseHelper = mDataBaseHelper(this, MDATABASE_NAME, null, 1)
        CookieEx = nHttp.nCookie()
        mSP = PreferenceManager.getDefaultSharedPreferences(this)
        mSP = this.getSharedPreferences("QiuChenSP", android.content.Context.MODE_PRIVATE)
        val ck = mSP.getString("Cookie", "")
        if (ck.isNotEmpty()) {
            CookieEx.addAll(ck)//restore cookies session
        }
        mQueue = newRequestQueue(this)
    }
}
