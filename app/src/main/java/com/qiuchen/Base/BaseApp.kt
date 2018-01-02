package com.qiuchen.Base

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager

/**
 * Created by qiuchen on 2017/12/31.
 */
abstract class BaseApp : AppCompatActivity(), View.OnClickListener {
    lateinit var mSet: mLayoutSet
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSet = getSet()
        setContentView(mSet.layout)
        if (mSet.TranslateBar) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        InitOver()
    }

    abstract fun InitOver()


    /**
     * 获取基本布局设置
     */
    abstract fun getSet(mSet: mLayoutSet = mLayoutSet(0, BackNoCallBack = false, TranslateBar = true, doubleClickExit = true)): mLayoutSet


    /**
     * 当返回键按下的时候，先调用本方法，后判断事件响应，最后实现父类方法
     */
    abstract fun onPressBackKey(): Boolean


    var time: Long = 0
    override fun onBackPressed() {
        val result = onPressBackKey()
        if (result)
            return
        if (mSet.BackNoCallBack) {
            return
        }
        var time = System.currentTimeMillis()
        if (mSet.doubleClickExit && time - this.time > 2000) {
            this.time = time
            Snackbar.make(window.decorView, "请再按一次退出App", Snackbar.LENGTH_SHORT)
                    .setAction("退出", null)
                    .show()
            return
        } else if (mSet.doubleClickExit) {
            System.exit(0)
        }
        super.onBackPressed()
    }

    fun <T> go(cls: Class<T>) {
        startActivity(Intent(this, cls))//cls::class.java
    }
}