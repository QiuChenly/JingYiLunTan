package com.qiuchen.Adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.qiuchen.API.mJingYi
import com.qiuchen.DataModel.mTask
import com.qiuchen.R
import com.qiuchen.Utils.mUtils
import com.qiuchen.Utils.mUtilsJ
import com.qiuchen.View.WebView
import kotlinx.android.synthetic.main.item_danzi.view.*
import java.util.*

/**
 * @author QiuChenLuoYe 在 2018/1/1 下午11:02.
 * @since
 */
class DanZiAdapter(var mList: ArrayList<mTask>, var click: onItemClick, var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(

), View.OnClickListener, View.OnTouchListener {
    override fun onClick(p0: View?) {
        p0?.mTaskRV_TaskTitle?.isFocusable = true
        click.onTaskItemBeClick(p0?.tag as Int, p0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return navigationBarList.VH(LayoutInflater.from(parent.context).inflate(R.layout.item_danzi, parent, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mTasks = mList[position]
        val mAnimal = AnimationUtils.loadAnimation(context, R.anim.recyclerview_item)
        holder.itemView.startAnimation(mAnimal)
        with(holder.itemView) {
            this.tag = position
            mTaskRV_COST.text = mTasks.预算价格
            mTaskRV_TaskTitle.text = mTasks.订单帖子标题
            mTaskRV_Author.text = mTasks.下单方
            mTaskRV_NEEDCODE.text = mTasks.是否要源码
            mTaskRV_SENDTIME.text = mTasks.发布时间
            setOnClickListener(this@DanZiAdapter)
            setOnTouchListener(this@DanZiAdapter)
            setOnClickListener { view: View ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.startActivity(
                            Intent(view.context, WebView::class.java).putExtra("url", mTasks.url),
                            ActivityOptions.makeSceneTransitionAnimation(context as Activity, view, "mTranslates").toBundle())
                } else context.startActivity(
                        Intent(view.context, WebView::class.java).putExtra("url", mTasks.url))
            }
        }
        if (mUtils.hasNet(this.context))
            Glide.with(this.context)
                    .load(mJingYi.DEFAULT_PIC)
                    .into(holder.itemView.civ_XiaDanFangPic)
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        return mUtilsJ.onTouch(view, motionEvent)
    }

    fun getItemUrl(position: Int): String {
        return mList[position].url
    }

    fun getItemAuthorUrl(position: Int): String {
        return mList[position].下单方Url
    }

    fun setData(data: ArrayList<mTask>) {
        mList = data
        notifyDataSetChanged()
        //加入数据库
        for (mTask in mList) {
            mUtils.mDataBaseHelper.addItem(mTask)
        }
    }

    lateinit var tempArray: ArrayList<mTask>

    fun addData(data: ArrayList<mTask>, rec: RecyclerView) {
        if (mList.size <= 0) {
            setData(data)
            return
        }
        tempArray = ArrayList()
        for (tas in data) {
            if (!isHas(tas.url)) {
                mUtils.mDataBaseHelper.addItem(tas)
                tempArray.add(0, tas)
            } else {
                //暂不实现
                break
            }
        }
        if (tempArray.isNotEmpty()) {
            for (temp in tempArray) {
                mList.add(0, temp)
                notifyItemInserted(0)
                rec.scrollToPosition(0)
            }
        }
    }

    fun isHas(str: String): Boolean {
        return mList.any { it.url.contains(str) }
    }

    interface onItemClick {
        fun onTaskItemBeClick(position: Int, view: View)
    }
}
