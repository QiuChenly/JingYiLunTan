package com.qiuchen.UI.Activity

import android.animation.ObjectAnimator
import android.app.FragmentTransaction
import android.os.Build
import android.transition.Slide
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.qiuchen.Base.BaseActivity
import com.qiuchen.Datas.MainData
import com.qiuchen.Models.MainModel
import com.qiuchen.R
import com.qiuchen.UI.Fragment.ForumOrderFragment
import com.qiuchen.UI.Fragment.ForumResources
import com.qiuchen.UI.Fragment.MenuFragment
import com.qiuchen.Views.MainView
import kotlinx.android.synthetic.main.layout_main.*

/**
 * @author QiuChenLuoYe 2018年03月18日 20时37分 创建.
 * @since
 */
class MainActivityEx : BaseActivity<MainView, MainModel, MainData>(), MainView {


    override fun getLayout() = R.layout.layout_main
    override fun createView() = this
    override fun createPresenter() = MainData()
    override fun onCreated() {
        setSupportActionBar(tb_Content)
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        val toggle = ActionBarDrawerToggle(this,drawer_layout,tb_Content,R.string.navi_open,R.string.navi_close)
//        drawer_layout.setDrawerListener(toggle)
//        toggle.syncState()

        //进场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Slide().setDuration(200)
            window.exitTransition = Slide().setDuration(200)
        }

        //开启4.4特性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        //偷懒模式开启
        //坚决不做viewpager
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_menu, MenuFragment())
                .commit()
        tv_title.text = "精易助手"
    }

    fun show() {
        val objectAnimator = ObjectAnimator.ofFloat(supportActionBar, "translationY", 0f)
        objectAnimator.duration = 200
        objectAnimator.interpolator = AccelerateInterpolator()
        objectAnimator.start()
    }

    fun switchViews(position: Int, title: String) {
        tv_title.text = title
        supportFragmentManager.beginTransaction().replace(R.id.fl_mainContent,
                when (position) {
                    0 -> {
                        ForumResources()
                    }
                    1 -> {
                        ForumOrderFragment()
                    }
                    else -> {
                        android.support.v4.app.Fragment()
                    }
                })
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        if (drawer_layout.isDrawerOpen(Gravity.START)) drawer_layout.closeDrawer(Gravity.START)
    }


    //    private var last = 0L
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        /* if (keyCode == KeyEvent.KEYCODE_BACK) {
             if (System.currentTimeMillis() - last > 2000) {
                 Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show()
                 last = System.currentTimeMillis()
             } else
                 System.exit(0)
             return true
         }*/
        if (keyCode == KeyEvent.KEYCODE_BACK && drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawer(Gravity.START)
            return true
        }
        return super.onKeyUp(keyCode, event)
    }
}
