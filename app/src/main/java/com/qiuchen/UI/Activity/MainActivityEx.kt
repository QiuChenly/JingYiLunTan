package com.qiuchen.UI.Activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.qiuchen.Adapter.MainFragmentPagerAdapter
import com.qiuchen.R
import kotlinx.android.synthetic.main.layout_main.*

/**
 * @author QiuChenLuoYe 2018年03月18日 20时37分 创建.
 * @since
 */
class MainActivityEx : FragmentActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        ll_MainPage_icon.showGreen(true, 0)
        ll_GuanShui_icon.showGreen(true, 1)
        ll_JieCare_icon.showGreen(true, 2)
        ll_user_icon.showGreen(true)

        ll_MainPage_title.showGreen(true)
        ll_GuanShui_title.showGreen(true)
        ll_JieCare_title.showGreen(true)
        ll_user_title.showGreen(true)

        when (v?.id) {
            ll_MainPage.id -> {
                ll_MainPage_icon.showGreen(false, 0)
                ll_MainPage_title.showGreen()
                getNowPage(1)
            }
            ll_GuanShui.id -> {
                ll_GuanShui_icon.showGreen(false, 1)
                ll_GuanShui_title.showGreen()
                getNowPage(2)
            }

            ll_JieCare.id -> {
                ll_JieCare_icon.showGreen(false, 2)
                ll_JieCare_title.showGreen()
                getNowPage(3)
            }

            ll_user.id -> {
                ll_user_icon.showGreen(false)
                ll_user_title.showGreen()
                getNowPage(4)
            }
        }
    }

    private fun getNowPage(int: Int) {
        /*
        supportFragmentManager.beginTransaction().addToBackStack(null)
                .commitAllowingStateLoss()
        */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        //适配多彩状态栏?怕不是喝多了吃饱了撑的。
        /*window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }*/

        ll_MainPage.setOnClickListener(this)
        ll_GuanShui.setOnClickListener(this)
        ll_JieCare.setOnClickListener(this)
        ll_user.setOnClickListener(this)


        //init all fragments
        val list = arrayListOf<Fragment>(Fragment(), Fragment(), Fragment())

        //init all fragment for master page
        // TODO : tomorrow fix some Bugs and add some new features in here. 18/4/18



        SNSVP_mainContent.adapter = MainFragmentPagerAdapter(supportFragmentManager,list)


        //init first page by search information
        ll_MainPage.callOnClick()
    }

    fun TextView.showGreen(black: Boolean = false) {
        if (black) setTextColor(Color.parseColor("#747474")) else setTextColor(Color.parseColor("#1b9d5e"))
    }

    fun ImageView.showGreen(black: Boolean = false, position: Int = 4) {
        val image = when (position) {
            0 -> {
                if (black) {
                    R.drawable.ic_home_black_24dp
                } else {
                    R.drawable.ic_home_green_24dp
                }
            }
            1 -> {
                if (black) {
                    R.drawable.ic_feedback_black_24dp
                } else {
                    R.drawable.ic_feedback_green_24dp
                }
            }
            2 -> {
                if (black) {
                    R.drawable.ic_group_black_24dp
                } else {
                    R.drawable.ic_group_green_24dp
                }
            }
            else -> {
                if (black) {
                    R.drawable.ic_account_circle_black_24dp
                } else {
                    R.drawable.ic_account_circle_green_24dp
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            background = getDrawable(image)
        } else
            Toast.makeText(this.context, "Android 5.0下不支持此特性！", Toast.LENGTH_SHORT).show()
    }
//    override fun onCreated() {
//        setSupportActionBar(tb_Content)
//        supportActionBar?.apply {
//            title = ""
//            setHomeButtonEnabled(true)
////            setDisplayHomeAsUpEnabled(true)  不是二级页面，取消显示
//        }
//        val toggle = ActionBarDrawerToggle(this, drawer_layout, tb_Content, R.string.navi_open, R.string.navi_close)
//        drawer_layout.setDrawerListener(toggle)
//        toggle.syncState()
//
//        //进场动画
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.enterTransition = Slide().setDuration(200)
//            window.exitTransition = Slide().setDuration(200)
//        }
//
//        //开启4.4特性
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//
//        //偷懒模式开启
//        //坚决不做viewpager
//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.fl_menu, MenuFragment())
//                .commit()
//        tv_title.text = "精易助手"
//    }
//
//    fun show() {
//        val objectAnimator = ObjectAnimator.ofFloat(supportActionBar, "translationY", 0f)
//        objectAnimator.duration = 200
//        objectAnimator.interpolator = AccelerateInterpolator()
//        objectAnimator.start()
//    }
//
//    fun switchViews(position: Int, title: String) {
//        tv_title.text = title
//        supportFragmentManager.beginTransaction().replace(R.id.fl_mainContent,
//                when (position) {
//                    0 -> {
//                        ForumResources()
//                    }
//                    1 -> {
//                        ForumOrderFragment()
//                    }
//                    2 -> {
//                        HappyFragment()
//                    }
//                    else -> {
//                        android.support.v4.app.Fragment()
//                    }
//                })
//                .addToBackStack(null)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit()
//        if (drawer_layout.isDrawerOpen(Gravity.START)) drawer_layout.closeDrawer(Gravity.START)
//    }
}
