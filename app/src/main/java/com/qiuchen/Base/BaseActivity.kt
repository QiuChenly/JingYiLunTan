package com.qiuchen.Base

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater

/**
 * @author QiuChenLuoYe 2018年03月18日 18时12分 创建.
 * @since
 */
abstract class BaseActivity<V : BaseView, M : BaseModel, P : BasePresenter<V, M>> : AppCompatActivity() {

    abstract fun getLayout(): Int
    abstract fun onCreated()
    abstract fun createView(): V
    abstract fun createPresenter(): P

    private var view: V? = null
    private var mPres: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LayoutInflater.from(this).inflate(getLayout(), null))
        view = createView()
        mPres = createPresenter()
        if (view != null) mPres?.attach(view!!)
        onCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view != null && mPres != null) mPres?.detach()
    }

    fun getPres(): P? {
        return mPres
    }

    fun appInstalled(str: String): Boolean {
        packageManager.getInstalledPackages(0)
                .forEach {
                    if (it.packageName == str) {
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
}
