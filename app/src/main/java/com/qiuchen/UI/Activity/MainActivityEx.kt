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
import com.qiuchen.UI.Fragment.ForumByMe
import com.qiuchen.UI.Fragment.SearchFragment
import kotlinx.android.synthetic.main.layout_main.*

/**
 * @author QiuChenLuoYe 2018年03月18日 20时37分 创建.
 * @since
 */
class MainActivityEx : FragmentActivity(), View.OnClickListener {
    override fun onClick(v: View) {
        if (v.tag == lastSelect) return
        when (lastSelect) {
            0 -> {
                ll_MainPage_icon.showGreen(true, 0)
                ll_MainPage_title.showGreen(true)
            }
            1 -> {
                ll_GuanShui_icon.showGreen(true, 1)
                ll_GuanShui_title.showGreen(true)
            }
            2 -> {
                ll_JieCare_icon.showGreen(true, 2)
                ll_JieCare_title.showGreen(true)
            }
            3 -> {
                ll_user_icon.showGreen(true)
                ll_user_title.showGreen(true)
            }
            else -> {

            }
        }

        when (v.tag) {
            0 -> {
                ll_MainPage_icon.showGreen(false, 0)
                ll_MainPage_title.showGreen()
            }
            1 -> {
                ll_GuanShui_icon.showGreen(false, 1)
                ll_GuanShui_title.showGreen()
            }

            2 -> {
                ll_JieCare_icon.showGreen(false, 2)
                ll_JieCare_title.showGreen()
            }

            3 -> {
                ll_user_icon.showGreen(false)
                ll_user_title.showGreen()
            }
        }
        getNowPage(v.tag as Int)
        lastSelect = v.tag as Int
    }

    private var lastSelect = -1

    private fun getNowPage(int: Int) {
        SNSVP_mainContent.currentItem = int
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        //适配多彩状态栏?怕不是喝多了吃饱了撑的。
        /*window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }*/
        ll_MainPage.tag = 0
        ll_MainPage.setOnClickListener(this)
        ll_GuanShui.tag = 1
        ll_GuanShui.setOnClickListener(this)
        ll_JieCare.tag = 2
        ll_JieCare.setOnClickListener(this)
        ll_user.tag = 3
        ll_user.setOnClickListener(this)

        //init all fragments
        val list = arrayListOf(SearchFragment(),
                Fragment(),
                Fragment(),
                ForumByMe())

        //init all fragment for master page
        // TODO : tomorrow fix some Bugs and add some new features in here. 18/4/18
        SNSVP_mainContent.adapter = MainFragmentPagerAdapter(supportFragmentManager, list)
        SNSVP_mainContent.offscreenPageLimit = 4
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
}
