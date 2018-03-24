package com.qiuchen.UI.Activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import com.qiuchen.Base.BaseActivity
import com.qiuchen.Base.BaseModel
import com.qiuchen.Datas.SplashData
import com.qiuchen.R
import com.qiuchen.Views.SplashView

/**
 * @author QiuChenLuoYe 2018年03月18日 18时34分 创建.
 * @since
 */
class SplashActivity : BaseActivity<SplashView, BaseModel, SplashData>(), SplashView {


    override fun createPresenter(): SplashData {
        return SplashData()
    }

    override fun getLayout(): Int {
        return R.layout.layout_splash
    }

    override fun createView(): SplashView {
        return this
    }

    override fun onCreated() {
        getPres()?.post(Runnable {
            val i = Intent(this, MainActivityEx::class.java)
            //兼容Android5.0之前的代码
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            } else {
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.bounce_interpolator)
            }
            finish()
        }, 3000)
    }
}
