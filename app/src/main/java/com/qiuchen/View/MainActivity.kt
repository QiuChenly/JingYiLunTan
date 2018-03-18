package com.qiuchen.View

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseApp(), navigationBarList.onItemClick, ViewPager_Content.ViewEvent, mJingYi.TaskCallBack, iGetMoreData, ViewPager.OnPageChangeListener, mJingYi.Companion.QRCallBack {

    override fun getImgSuccess(status: Int, bit: Bitmap?) {
        runOnUiThread {
            //通知系统图片加载
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/scan.png")))
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
                    mUserNick.text = mSharedContext.mLoginState.nickName + "(" + mSharedContext.mLoginState.uid + ")"
                    mUserNick.textSize = 15f
                    mUserPic.setImageBitmap(mSharedContext.mLoginState.pic)
                    mUserPic.setOnClickListener(null)
                    str = "欢迎你,易友 " + mSharedContext.mLoginState.nickName
                    mBottomControlBar.visibility = View.VISIBLE
                    mSharedContext.saveCookie()
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
                mContentTitle.text = "论坛接单(" + mShowCareList.count + ")"
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
        if (hasNet())
            mJingYi.getTaskList(this)
        else {
            Snackbar.make(window.decorView, "数据获取失败！请检查网络环境！", Snackbar.LENGTH_SHORT).show()
            taskOver()
        }
    }

    override fun taskGetFailed() {
        runOnUiThread {
            Snackbar.make(window.decorView, "数据获取失败！请检查网络环境！", Snackbar.LENGTH_SHORT).show()
            mContentTitle.text = "论坛接单(" + mShowCareList.count + ")"
        }
    }

    override fun taskCallback(a: ArrayList<mTask>) {
        runOnUiThread {
            Snackbar.make(window.decorView, "数据获取完成!", Snackbar.LENGTH_SHORT)
                    .show()
            mShowCareList.addDatas(a)
            mContentTitle.text = "论坛接单(" + mShowCareList.count + ")"
        }
    }

    override fun ViewPagerViewEvent(view: View, position: Int) {
        when (position) {
            0 -> {
                mShowCareList = ShowCareListJ(view, this, this)
                if (hasNet()) {
                    mJingYi.getTaskList(this)
                } else Toast.makeText(this, "无网络连接哦!", Toast.LENGTH_SHORT).show()
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
        mContentVP.offscreenPageLimit = 3
        mContentVP.adapter = ViewPager_Content(mViews, this)
        mContentVP.addOnPageChangeListener(this)

        //SomeClickCallback
        mNavigationExitUser.setOnClickListener(this)
        mToolBarMenu.setOnClickListener(this)
        mUserPic.setOnClickListener(this)
        mContentTitle.text = "论坛接单"

        //启动网络监测
        mSharedContext.hasNetwork = hasNet()
        if (mSharedContext.hasNetwork) {
            isNoNet = false
            if (mSharedContext.getCookie().toString().isEmpty()) {
                mPresenter.getQR(this)
            } else {
                mPresenter.autoLogin(this)
            }
        } else {
            mUserNick.text = "请连接网络再戳我~"
            isNoNet = true
        }
    }


    fun hasNet(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo?.isAvailable == true
    }

    override fun getSet(mSet: mLayoutSet): mLayoutSet {
        return mSet.apply {
            this.BackNoCallBack = false
            this.layout = R.layout.activity_main
            this.TranslateBar = true
        }
    }

    var isNoNet = true
    override fun onClick(p0: View) {
        when (p0.id) {
            mNavigationExitUser.id -> {
                onExitUser()
            }
            mToolBarMenu.id -> {
                drawer_layout.openDrawer(Gravity.START)
            }
            mUserPic.id -> {
                if (!hasNet()) {
                    return
                }
                if (hasNet() && isNoNet) {
                    mUserNick.text = "点击二维码登录精易"
                    getQR()
                    isNoNet = false
                    return
                }
                if (appInstalled("com.tencent.mm")) {
                    startWeiXin()
                    Toast.makeText(this, "请在微信中扫描出现的第一张二维码,即可登录论坛!", Toast.LENGTH_LONG)
                            .show()
                } else {
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
        drawer_layout.closeDrawer(Gravity.START)
        val bDialog = BottomSheetDialog(this)
        val v = LayoutInflater.from(this).inflate(R.layout.will_exit, null)
        with(v) {
            v.findViewById<TextView>(R.id.bottomSheet_willExit_title)
                    .text = "住手!${mSharedContext.mLoginState.nickName}...你敢?!"
            v.findViewById<TextView>(R.id.bottomSheet_willExit_body)
                    .text = "我有一言,请诸位静听:\n如果退出逐鹿中原,您将失去叱咤风云 快意恩仇的易语言江湖地位!复兴易语言的大业,将遥遥无期!\n值此业界大难之际,${mSharedContext.mLoginState.nickName}你又有何作为?"
            v.findViewById<Button>(R.id.bottomSheet_willExit_keep)
                    .setOnClickListener {
                        bDialog.dismiss()
                    }
            v.findViewById<Button>(R.id.bottomSheet_willExit_exits)
                    .setOnClickListener {
                        Toast.makeText(it.context, "社会社会,壮士好走!", Toast.LENGTH_SHORT).show()
                        bDialog.dismiss()
                        mSharedContext.getCookie().initStatus()
                        //调用加载二维码方法
                        getQR()
                        mUserNick.text = "点击二维码登录精易"
                        mBottomControlBar.visibility = View.GONE
                        mSharedContext.saveCookie() //清空Cookie
                    }
        }
        bDialog.setContentView(v)
        bDialog.show()
    }

    private fun getQR() {
        if (hasNet()) {
            if (mSharedContext.getCookie().toString().isEmpty()) {
                mPresenter.getQR(this)
                mUserPic.setOnClickListener(this)
            } else {
                mPresenter.autoLogin(this)
            }
        }
    }
}
