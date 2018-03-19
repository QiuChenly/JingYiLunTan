package com.qiuchen.UI.Activity

import android.os.Build
import android.transition.Slide
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import com.qiuchen.Base.BaseActivity
import com.qiuchen.Datas.MainData
import com.qiuchen.Models.MainModel
import com.qiuchen.R
import com.qiuchen.UI.Fragment.ForumOrderFragment
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
        //进场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Slide().setDuration(200)
            window.exitTransition = Slide().setDuration(200)
        }

        //开启4.4特性
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        fl_mToolBarMenu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.START)
        }

        //偷懒模式开启
        //坚决不做viewpager
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_menu, MenuFragment())
                .commit()
    }

    fun switchViews(position: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_mainContent,
                when (position) {
                    0 -> {
                        ForumOrderFragment()
                    }
                    else -> {
                        ForumOrderFragment()
                    }
                }).commit()
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawer(Gravity.START)
            return true
        }
        return super.onKeyUp(keyCode, event)
    }
}
