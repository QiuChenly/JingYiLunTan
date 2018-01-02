package com.qiuchen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.qiuchen.Utils.mDataBaseHelper
import com.qiuchen.Utils.mUtils

/**
 * Created by qiuchen on 2018/1/1.
 */
class mSharedContext : Application() {

    private val MDATABASE_NAME = "mDB.db"
    override fun onCreate() {
        super.onCreate()
        mContext = this.applicationContext
        mUtils.mDataBaseHelper = mDataBaseHelper(this, MDATABASE_NAME, null, 1)
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context

        fun getContext(): Context {
            return this.mContext
        }
    }
}