package com.qiuchen.Presenter

import android.util.Log
import com.qiuchen.API.mJingYi

/**
 * Created by qiuchen on 2018/1/1.
 */
class mPresenter {
    companion object {
        lateinit var QRRefrashThread: Thread
        var TAG = "QiuChen"
        fun getQR(cb: mJingYi.Companion.QRCallBack) {
            Log.d(TAG, "进入二维码刷新")
            QRRefrashThread = Thread {
                kotlin.run {
                    Log.d(TAG, "初始化二维码刷新")
                    mJingYi.initScanQR(cb)
                    var a = 0
                    while (true) {
                        Log.d(TAG, "二维码刷新计次" + a.toString())
                        Thread.sleep(1000)
                        a++
                        var s = mJingYi.getQRStatus()
                        if (s == 1) {
                            val a = mJingYi.ScanSuccess()
                            cb.getImgSuccess(1, null)
                            break
                        }
                        if (a >= 30) {
                            mJingYi.initScanQR(cb)
                            a = 0
                        }
                    }
                }
            }
            Log.d(TAG, "正式启动二维码刷新")
            QRRefrashThread.start()
        }

        fun autoLogin(cb: mJingYi.Companion.QRCallBack) {
            Thread {
                kotlin.run {
                    mJingYi.getMainIndex()
                    cb.getImgSuccess(1, null)
                }
            }.start()
        }
    }
}