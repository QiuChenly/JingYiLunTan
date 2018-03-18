package com.qiuchen.Base

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

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
        //针对Android M+系统做适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(getPermission(), 0)
        } else {
            InitOver()
        }
    }

    abstract fun InitOver()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        InitOver()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun getPermission(): Array<String> {
        return arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE)
    }

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

    fun appInstalled(str: String): Boolean {
        packageManager.getInstalledPackages(0)
                .forEach {
                    val pi = it
                    if (pi.packageName == str) {
                        return true
                    }
                }
        return false
    }

    fun startWeiXin() {
        val c = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
        val i = Intent()
        i.component = c
        i.action = Intent.ACTION_MAIN
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }

    fun <T> go(cls: Class<T>) {
        startActivity(Intent(this, cls))//cls::class.java
    }
}
