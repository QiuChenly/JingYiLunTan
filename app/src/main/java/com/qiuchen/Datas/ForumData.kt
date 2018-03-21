package com.qiuchen.Datas

import com.google.gson.Gson
import com.qiuchen.API.BaseRequest
import com.qiuchen.API.mJingYi
import com.qiuchen.Base.BasePresenter
import com.qiuchen.DataModel.mTask
import com.qiuchen.DataModel.mTaskList
import com.qiuchen.Models.ForumModel
import com.qiuchen.Views.ForumView
import java.util.regex.Pattern
import kotlin.concurrent.thread

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午3:11.
 * @since
 */
class ForumData : BasePresenter<ForumView, ForumModel>() {
    override fun createModel(): ForumModel? {
        return ForumModel()
    }

    companion object {
        private const val DELAY_REFRESH = 2000L
    }

    fun hand() {
        thread {
            while (getView() != null) {
                println("thread" + getView())
                getList()
                Thread.sleep(DELAY_REFRESH)
            }
        }
    }

    fun getList() {
        getModel()?.getList(object : BaseRequest.RequestCallBack {
            override fun onSuccess(it: String) {
                val d = Gson().fromJson(it, mTaskList::class.java)
                val p = Pattern.compile(mJingYi.TASK_REGEX)
                val m = p.matcher(d.data)
                val e = ArrayList<mTask>()
                while (m.find()) {
                    val f = mTask()
                    //thread-14109607-1-1.html + https://bbs.125.la/
                    f.url = "https://bbs.125.la/" + m.group(1)
                    f.订单帖子标题 = m.group(2)
                    f.预算价格 = m.group(3)
                    f.是否要源码 = m.group(4)
                    f.发布时间 = m.group(5).replace("&nbsp;", "")
                    f.下单方Url = m.group(6)
                    f.下单方 = m.group(7)
                    f.联系下单方 = m.group(8)
                    e.add(f)
                }
                if (getView() != null) {
                    println(getView())
                    post(Runnable {
                        getView()?.getList(true, e, "Succ")
                    })
                }
            }

            override fun onFailed(Reason: String) {
                if (getView() != null) post(Runnable {
                    getView()?.getList(false, null, Reason)
                })
            }
        })
    }
}