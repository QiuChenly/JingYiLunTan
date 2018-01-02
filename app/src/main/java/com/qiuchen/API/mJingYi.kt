package com.qiuchen.API

import com.google.gson.Gson
import com.qiuchen.DataModel.mTask
import com.qiuchen.DataModel.mTaskList
import com.qiuchen.R
import com.qiuchen.httpClient.httpClient
import java.util.regex.Pattern
import kotlin.concurrent.thread

/**
 * Created by qiuchen on 2018/1/1.
 */
class mJingYi {
    companion object {
        val TASK_REGEX = "<tr><td><a href=\"(.*?)\" title=\"(.*?)\" target=\"_blank\">(.*?)</a></td><td><font color=#.*?><b>(.*?)</b></font></td><td>(.*?)</td><td><span title=\"(.*?)\">(.*?)</span></td><td></td><td><a href=\"(.*?)\" target=\"_blank\">(.*?)</a></td><td><a href=\"(.*?)\" target=\"_blank\">(.*?)</a></td></tr>"
        val MAIN_URL = "https://bbs.125.la/"
        val GET_TASK_LIST = "https://bbs.125.la/plugin.php?id=e3600%3Atask&mod=show&type=1&s=1&a="
        val getTitle = arrayOf("论坛接单", "随机精华", "最新求助")
        val getImage = arrayOf(
                R.drawable.ic_monetization_on_black_24dp,
                R.drawable.ic_feedback_black_24dp,
                R.drawable.ic_sentiment_very_dissatisfied_black_24dp
        )

        fun getTaskList(t: TaskCallBack) {
            Thread {
                kotlin.run {
                    val response = httpClient.Request(GET_TASK_LIST + System.currentTimeMillis(), 0, null, httpClient.cookies, "Accept:*/*\n" +
                            "Accept-Language:zh-CN,zh;q=0.9,en;q=0.8\n" +
                            "Connection:keep-alive\n" +
                            "Host:bbs.125.la\n" +
                            "Referer:https://bbs.125.la/e3600-task.html\n" +
                            "User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36\n" +
                            "X-Requested-With:XMLHttpRequest", 10000, 10000, true)
                    t.taskOver()
                    if (response == null) {
                        t.taskGetFailed()
                        return@Thread
                    }
                    val a = httpClient.DecodeByteToString(response.responseByte, "gbk")
                    val d = Gson().fromJson(a, mTaskList::class.java)
                    val b = Pattern.compile(TASK_REGEX)
                    val c = b.matcher(d.data)
                    val e = ArrayList<mTask>()
                    while (c.find()) {
                        val f = mTask()
                        //thread-14109607-1-1.html + https://bbs.125.la/
                        f.url = "https://bbs.125.la/" + c.group(1)
                        f.下单方 = c.group(9)
                        f.发布时间 = (c.group(6) + "  " + c.group(7)).replace("&nbsp;", "")
                        f.是否要源码 = c.group(5)
                        f.结束日期 = c.group(1)
                        f.联系下单方 = c.group(10)
                        f.订单帖子标题 = c.group(3)
                        f.预算价格 = c.group(4)
                        f.下单方Url = c.group(8)
                        e.add(f)
                    }
                    t.taskCallback(e)
                }
            }.start()
        }
    }

    interface TaskCallBack {
        fun taskCallback(a: ArrayList<mTask>)
        fun taskGetFailed()
        fun taskOver()
    }
}


