package com.qiuchen

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.qiuchen.Utils.mDataBaseHelper
import com.qiuchen.Utils.mUtils
import com.qiuchen.jingyi.nativeHttp.nHttp


/**
 * Created by qiuchen on 2018/1/1.
 */
class mSharedContext : Application() {

    private val MDATABASE_NAME = "mDB.db"
    override fun onCreate() {
        super.onCreate()
        mContext = this.applicationContext
        mUtils.mDataBaseHelper = mDataBaseHelper(this, MDATABASE_NAME, null, 1)
        CookieEx = nHttp.nCookie()
        mSP = this.getSharedPreferences("QiuChenSP", android.content.Context.MODE_PRIVATE)
        val ck = mSP.getString("Cookie", "")
        if (ck.isNotEmpty()) {
            CookieEx.addAll(ck)
        }
    }

    companion object {
        private lateinit var mContext: Context
        private lateinit var CookieEx: nHttp.nCookie
        private lateinit var mSP: SharedPreferences

        var hasNetwork = false

        var mLoginState = com.qiuchen.DataModel.mLoginState()

        fun getSP(): SharedPreferences {
            return mSP
        }

        fun saveCookie() {
            mSP.edit().putString("Cookie", CookieEx.toString())
                    .apply()
        }

        fun getContext(): Context {
            return this.mContext
        }

        fun getCookie(): nHttp.nCookie {
            return CookieEx
        }
    }
}
