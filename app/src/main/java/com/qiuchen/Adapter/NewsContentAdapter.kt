package com.qiuchen.Adapter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.qiuchen.API.mJingYi
import com.qiuchen.Base.BaseVH
import com.qiuchen.DataModel.mNewsModel
import com.qiuchen.R
import com.qiuchen.Views.NewsView
import kotlinx.android.synthetic.main.item_special_normal.view.*
import kotlinx.android.synthetic.main.item_special_viewpagerandhead.view.*
import kotlin.concurrent.thread

class NewsContentAdapter(var mlist: List<mNewsModel>, val show: List<String>, val SearchV: NewsView) : RecyclerView.Adapter<BaseVH>(), Handler.Callback, ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        default = position
        showPosition(default)
    }

    private var default = 0

    override fun handleMessage(msg: Message?): Boolean {
        default++
        if (default >= show.size) {
            default = 0
        }
        synchronized(this) {
            if (VP != null) {
                VP?.currentItem = default
                showPosition(default)
                hand?.sendMessageDelayed(Message(), 3000)
            } else {
                hand = null
            }
        }
        return true
    }

    fun showPosition(position: Int) {
        for (i in 0..4) {
            val im = ll_doselects?.getChildAt(i) as ImageView
            if (i == position)
                im.setImageResource(R.drawable.donet_select)
            else
                im.setImageResource(R.drawable.donet_dark)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent?.context).inflate(when (viewType) {
            SPECIAL_VIEWPAGERANDHEAD -> R.layout.item_special_viewpagerandhead
            else -> R.layout.item_special_normal
        }, parent, false))
    }

    fun setList(mlist: List<mNewsModel>) {
        this.mlist = mlist
    }

    companion object {
        private const val SPECIAL_VIEWPAGERANDHEAD = 1;
        private const val SPECIAL_NORMAL = 2;
    }

    //special view first
    override fun getItemViewType(position: Int) = if (position == 0) SPECIAL_VIEWPAGERANDHEAD else SPECIAL_NORMAL

    private var VP: ViewPager? = null
    private var ll_doselects: LinearLayout? = null

    override fun getItemCount() = mlist.size + 1
    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        with(holder.itemView) {
            when (getItemViewType(position)) {
                SPECIAL_VIEWPAGERANDHEAD -> {
                    val view = ViewPager_Content(show, object : ViewPager_Content.ViewEvent {
                        override fun ViewPagerViewEvent(view: View, position: Int) {
                            //TODO 未实现相关跳转
                            SearchV.onShowPageBeClick("guer")
                            Toast.makeText(holder.itemView.context, "孤儿", Toast.LENGTH_SHORT).show()
                        }
                    })
                    VP = vp_searchShowsVP
                    ll_doselects = ll_doselect
                    vp_searchShowsVP.setOnPageChangeListener(this@NewsContentAdapter)
                    vp_searchShowsVP.adapter = view
                }
                SPECIAL_NORMAL -> {
                    val i = mlist[position - 1]
                    tv_username.text = i.info.postname
                    //optimization shows
                    tv_posttime.text = i.info.sendof.replace("/", " ")
                    tv_title.text = i.info.title
                    tv_from.text = "来自 ${i.category}"

                    val url = i.tidHref!!
                    thread {
                        mJingYi.getBBSPageInfo(url, object : mJingYi.Companion.BBSPageInfoGet {
                            override fun getSuccess(status: Int, ret: mJingYi.Companion.userInfos) {
                                val hand = Handler(Looper.getMainLooper())
                                hand.post {
                                    tv_viewed.text = ret.watch
                                    tv_message.text = ret.repost
                                    Glide.with(holder.itemView.context)
                                            .load(ret.imgUrl)
                                            .into(mUserPic)
                                }
                            }
                        })
                    }.start()

                    if (i.money != null && i.money!!.isNotEmpty()) {
                        tv_money.text = i.money
                        tv_money.visibility = View.VISIBLE
                    } else tv_money.visibility = View.INVISIBLE
                }
            }
        }
    }

    private var hand: Handler? = null

    fun startLoop() {
        hand = Handler(this)
        hand?.sendMessageDelayed(Message(), 3000)
    }

    fun stopLoop() {
        hand = null
        VP = null
    }
}