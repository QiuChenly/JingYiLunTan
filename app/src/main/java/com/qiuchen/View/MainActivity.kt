package com.qiuchen.View

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils.isEmpty
import android.view.Gravity
import android.view.View
import com.qiuchen.API.mJingYi
import com.qiuchen.Adapter.ViewPager_Content
import com.qiuchen.Adapter.navigationBarList
import com.qiuchen.Base.BaseApp
import com.qiuchen.Base.mLayoutSet
import com.qiuchen.DataModel.mTask
import com.qiuchen.Presenter.mPresenter
import com.qiuchen.R
import com.qiuchen.Utils.mUtils
import com.qiuchen.mSharedContext
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_head.*
import java.io.File
import java.io.FileOutputStream
import java.security.Permission
import java.security.PermissionCollection
import java.security.Permissions
import java.util.HashMap
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class MainActivity : BaseApp(), navigationBarList.onItemClick, ViewPager_Content.ViewEvent, mJingYi.TaskCallBack, iGetMoreData, ViewPager.OnPageChangeListener, mJingYi.Companion.QRCallBack {

    override fun getImgSuccess(status: Int, bit: Bitmap?) {
        runOnUiThread {
            var str = ""
            when (status) {
                0 -> {
                    str = "二维码已刷新！"
                    mUserPic.setImageBitmap(bit)
                }
                -1 ->
                    str = "二维码获取失败！请检查网络环境！"
                3 ->
                    str = "刷新了二维码！"
                1 -> {
                    mUserNick.text = mSharedContext.mLoginState.nickName + " Uid:" + mSharedContext.mLoginState.uid
                    mUserNick.textSize = 15f
                    mUserPic.setImageBitmap(mSharedContext.mLoginState.pic)
                    str = "欢迎你,开发者 " + mSharedContext.mLoginState.nickName
                    mBottomControlBar.visibility = View.VISIBLE
                }
            }
            Snackbar.make(window.decorView, str, Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        when (position) {
            0 ->
                mContentTitle.text = "论坛接单(" + mShowCareList.getCount() + ")"
            else -> {
                mContentTitle.text = mJingYi.getTitle[position]
            }
        }
    }

    override fun onPageSelected(position: Int) {

    }

    override fun taskOver() {
        runOnUiThread {
            with(mShowCareList.refresh) {
                if (this.isRefreshing)
                    this.isRefreshing = false
            }
        }
    }

    override fun getMoreData() {
        mJingYi.getTaskList(this)
    }

    override fun taskGetFailed() {
        runOnUiThread {
            Snackbar.make(window.decorView, "数据获取失败！请检查网络环境！", Snackbar.LENGTH_SHORT).show()
            mContentTitle.text = "论坛接单(" + mShowCareList.getCount() + ")"
        }
    }

    override fun taskCallback(a: ArrayList<mTask>) {
        runOnUiThread {
            Snackbar.make(window.decorView, "数据获取完成!", Snackbar.LENGTH_SHORT)
                    .show()
            mShowCareList.addDatas(a)
            mContentTitle.text = "论坛接单(" + mShowCareList.getCount() + ")"
        }
    }

    override fun ViewPagerViewEvent(view: View, position: Int) {
        when (position) {
            0 -> {
                mShowCareList = ShowCareListJ(mViews[0], this, this)
                mJingYi.getTaskList(this)
            }
            1 -> {
            }
            2 -> {
            }
        }
    }

    override fun onPressBackKey(): Boolean {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }

    lateinit var mViews: List<View>

    lateinit var mShowCareList: ShowCareListJ

    override fun InitOver() {
        mNavigationList.layoutManager = LinearLayoutManager(this)
        val adapterData = ArrayList<Map<Int, String>>()
        for ((a, str) in mJingYi.getTitle.withIndex()) {
            val map = HashMap<Int, String>()
            map[mJingYi.getImage[a]] = str
            adapterData.add(map)
        }
        mUtils.size = mJingYi.getTitle.size
        mNavigationList.adapter = navigationBarList(adapterData, this)

        //初始化桌面
        mViews = arrayListOf(
                layoutInflater.inflate(R.layout.activity_show_list, null),
                layoutInflater.inflate(R.layout.activity_randomgoodsource, null),
                layoutInflater.inflate(R.layout.activity_lastedhelp, null)
        ).toList()
        mContentVP.offscreenPageLimit = 10
        mContentVP.adapter = ViewPager_Content(mViews, this)
        mContentVP.addOnPageChangeListener(this)

        //SomeClickCallback
        mNavigationExitUser.setOnClickListener(this)
        mToolBarMenu.setOnClickListener(this)
        mUserPic.setOnClickListener(this)
        mContentTitle.text = "论坛接单"

        if (mSharedContext.getCookie().toString().isEmpty()) {
            mPresenter.getQR(this)
        } else {
            mPresenter.autoLogin(this)
        }
    }

    override fun getSet(mSet: mLayoutSet): mLayoutSet {
        return mSet.apply {
            this.BackNoCallBack = false
            this.layout = R.layout.activity_main
            this.TranslateBar = true
        }
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            mNavigationExitUser.id -> {
                onExitUser()
            }
            mToolBarMenu.id -> {
                drawer_layout.openDrawer(Gravity.START)
            }
            mUserPic.id -> {
                if (appInstalled("com.tencent.mm")) {
                    startWeiXin()
                }else{
                    Snackbar.make(window.decorView, "您还没有安装微信!", Snackbar.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

    /**
     * NavigationItem点击联动VP
     */
    override fun onItemClick(position: Int) {
        mContentVP.currentItem = position
        drawer_layout.closeDrawer(Gravity.START)
    }

    /**
     * 用户退出登录
     */
    fun onExitUser() {
        mSharedContext.getCookie().initStatus() //清空Cookie
        //调用加载二维码方法
        mPresenter.getQR(this)
        mUserNick.text = "QiuChenly - 扫码登录精易"
        mBottomControlBar.visibility = View.GONE
    }
}
