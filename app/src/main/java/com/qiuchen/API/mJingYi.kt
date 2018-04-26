package com.qiuchen.API

import android.graphics.Bitmap
import android.os.Environment
import com.google.gson.Gson
import com.qiuchen.DataModel.mNewsModel
import com.qiuchen.DataModel.mTask
import com.qiuchen.DataModel.mTaskList
import com.qiuchen.jingyi.nativeHttp.nHttp
import com.qiuchen.mSharedContext
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.util.regex.Pattern

/**
 * @author QiuChenLuoYe 在 2018/1/1 下午10:30.
 * @since
 */
class mJingYi {
    companion object {
        val TAG = "QiuChen"
        val TASK_REGEX = "<a href=\"(.*?)\" title=\"点击查看该订单的帖子\" target=\"_blank\">(.*?)<\\/a><\\/td><td><font color=#FF6600><b>(.*?)<\\/b><\\/font><\\/td><td>(.*?)<\\/td><td>(.*?)<\\/td><td><\\/td><td><a href=\"(.*?)\" target=\"_blank\">(.*?)<\\/a><\\/td><td><a href=\"(.*?)\" target=\"_blank\">点击联系<\\/a>"
        val MAIN_URL = "https://bbs.125.la/"
        const val TASK_LIST = "https://bbs.125.la/plugin.php?id=e3600%3Atask&mod=show&type=1&s=1&a="
        const val DEFAULT_PIC = "https://bbs.125.la:443/uc_server/images/noavatar_small.gif"
        const val RUANJIAN_SHOW = "https://bbs.125.la/forum.php?mod=forumdisplay&fid=165"
        const val RUANJIAN_SHOWMORE = "https://bbs.125.la/forum.php?mod=forumdisplay&fid=165&page=%s&t=7430237"
        fun getTaskList(t: TaskCallBack) {
            Thread {
                kotlin.run {
                    val http = nHttp.Builder(TASK_LIST + System.currentTimeMillis())
                            .setCookieStore(mSharedContext.getCookie())
                            .setRequestHeader("Accept", "*/*")
                            .setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                            .setRequestHeader("Connection", "keep-alive")
                            .setRequestHeader("Host", "bbs.125.la")
                            .setRequestHeader("Referer", "https://bbs.125.la/e3600-task.html")
                            .setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
                            .setRequestHeader("X-Requested-With", "XMLHttpRequest")
                            .Request()
                    t.taskOver()
                    if (http.getStatusCode() != 200) {
                        t.taskGetFailed()
                        return@Thread
                    }
                    val a = http.toString()
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

        //2018.1.24 开始全线使用nativeHttp
        private var QRLOGIN_INITURL = "https://bbs.125.la/plugin.php?id=we_weixin&mod=login"

        //获取二维码
        fun initScanQR(cb: QRCallBack) {
            val http = nHttp.Builder(QRLOGIN_INITURL)
                    .setCookieStore(mSharedContext.getCookie())
                    .setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .setRequestHeader("Cache-Control", "no-cache")
                    .setRequestHeader("Connection", "keep-alive")
                    .setRequestHeader("Host", "bbs.125.la")
                    .setRequestHeader("Pragma", "no-cache")
                    .setRequestHeader("Referer", "https://bbs.125.la/")
                    .setRequestHeader("Upgrade-Insecure-Requests", "1")
                    .setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .Request()
            if (http.getStatusCode() != 200) {
                cb.getImgSuccess(-1, null)
                return
            }
            var Str = http.toString()
            Str = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + getSubString(Str, "https://mp\\.weixin\\.qq\\.com/cgi-bin/showqrcode\\?ticket=(.*?)\" width=\"250\"")
            cb.getImgSuccess(0, readImg(Str, 0))
        }

        private fun readImg(str: String, mode: Int): Bitmap? {
            val http = nHttp.Builder(str)
                    .setCookieStore(mSharedContext.getCookie())
                    .setRequestHeader("Accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                    .setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .setRequestHeader("Cache-Control", "no-cache")
                    .setRequestHeader("Connection", "keep-alive")
                    .setRequestHeader("Host", "mp.weixin.qq.com")
                    .setRequestHeader("Pragma", "no-cache")
                    .setRequestHeader("Referer", "https://bbs.125.la/plugin.php?id=we_weixin&mod=login")
                    .setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            if (mode == 1) {
                http.setRequestHeader("Host", "bbs.125.la")
                        .setRequestHeader("Referer", "https://bbs.125.la/")
            }
            val mHttpRet = http.Request()
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val file = File(Environment.getExternalStorageDirectory(), "scan.png")
                if (file.exists())
                    file.delete()
                val fos = FileOutputStream(file)
                fos.write(mHttpRet.getBytes())
                fos.flush()
                fos.close()
            }
            return mHttpRet.toImage()
        }

        //0 = No
        //1 = OK
        fun getQRStatus(): Int {
            var str = "https://bbs.125.la/plugin.php?id=we_weixin&mod=login&ac=refurbish&d=&s=" + System.currentTimeMillis()
            val http = nHttp.Builder(str)
                    .setCookieStore(mSharedContext.getCookie())
                    .setRequestHeader("Accept", "*/*")
                    .setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .setRequestHeader("Cache-Control", "no-cache")
                    .setRequestHeader("Connection", "keep-alive")
                    .setRequestHeader("Host", "bbs.125.la")
                    .setRequestHeader("Pragma", "no-cache")
                    .setRequestHeader("Referer", "https://bbs.125.la/plugin.php?id=we_weixin&mod=login")
                    .setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .setRequestHeader("X-Requested-With", "XMLHttpRequest")
                    .Request()
            //{"status":"0","data":""}
            str = http.toString()
            val state = Gson().fromJson(str, QRBack::class.java)
            return state.status?.toInt()!!
        }

        fun ScanSuccess() {
            var str = "https://bbs.125.la/plugin.php?id=we_weixin&mod=login&ac=success&d="
            var http = nHttp.Builder(str)
                    .setCookieStore(mSharedContext.getCookie())
                    .setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .setRequestHeader("Cache-Control", "no-cache")
                    .setRequestHeader("Connection", "keep-alive")
                    .setRequestHeader("Host", "bbs.125.la")
                    .setRequestHeader("Pragma", "no-cache")
                    .setRequestHeader("Referer", "https://bbs.125.la/plugin.php?id=we_weixin&mod=login")
                    .setRequestHeader("Upgrade-Insecure-Requests", "1")
                    .setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .Request()
            if (http.getStatusCode() == 302)
                getMainIndex()
        }

        fun getMainIndex() {
            val str = "https://bbs.125.la/"
            HttpHelper().execute(HttpHelper.Builder(str).Build(object : HttpHelper.Callback {
                override fun NetResult(isSuccess: Boolean, retData: String) {
                    var p = Pattern.compile("<strong class=\"vwmy qq\"><a href=\"\\./home\\.php\\?mod=space&amp;uid=(.*?)\" target=\"_blank\" title=\"访问我的空间\">(.*?)</a></strong>")
                    var m = p.matcher(retData)
                    if (m.find()) {
                        val nick = m.group(2)
                        val uid = m.group(1)
                        p = Pattern.compile("mod=space&amp;uid=260102\"><img src=\"(.*?)\" onerror")
                        m = p.matcher(retData)
                        m.find()
                        var imageUrl = m.group(1)
                        if (imageUrl.isEmpty())
                            imageUrl = DEFAULT_PIC
                        val bit = readImg(imageUrl.replace(":443", ""), 1)
                        mSharedContext.mLoginState.isLogin = true
                        mSharedContext.mLoginState.nickName = nick
                        mSharedContext.mLoginState.uid = uid
                        mSharedContext.mLoginState.pic = bit
                    } else {
                        mSharedContext.mLoginState.isLogin = false
                    }
                }
            }))

        }

        fun signBbs(hash: String) {
            val u = "https://bbs.125.la/plugin.php?id=dsu_paulsign:sign&operation=qiandao&infloat=0"
            val http = nHttp.Builder(u)
                    .setPostData("formhash=$hash&qdxq=kx&todaysay=")
                    .setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                    .setRequestHeader("Cache-Control", "no-cache")
                    .setRequestHeader("Connection", "keep-alive")
                    .setRequestHeader("Host", "bbs.125.la")
                    .setRequestHeader("Pragma", "no-cache")
                    .setRequestHeader("Referer", "https://bbs.125.la/")
                    .setRequestHeader("Upgrade-Insecure-Requests", "1")
                    .setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                    .setAllowRedirect(true)
                    .setCookieStore(mSharedContext.getCookie())
                    .Request()
            val str = http.toString()
            println(str)
        }

        fun getMainPageData(GetData: GetData) {
            val str = "https://bbs.125.la/"
            HttpHelper().execute(HttpHelper.Builder(str).Build(object : HttpHelper.Callback {
                override fun NetResult(isSuccess: Boolean, retData: String) {
                    if (retData.indexOf("value=\"我今天什么都不想说\">我今天什么都不想说") != -1) {
                        val left = retData.indexOf("<a href=\"./member.php?mod=logging&amp;action=logout&amp;formhash=", 0)
                        signBbs(retData.substring(left, retData.indexOf("\"", left + "<a href=\"./member.php?mod=logging&amp;action=logout&amp;formhash=".length)))
                        return getMainPageData(GetData = GetData)
                    }
                    if (retData.indexOf("show_1") == -1) {
                        GetData.getDataSuccess(ArrayList())
                        return
                    }
                    val document = Jsoup.parse(retData)
                    val mainList = ArrayList<ArrayList<mNewsModel>>()
                    var arr: ArrayList<mNewsModel>
                    for (i in 1..7) {
                        if (i == 4) continue
                        val shows = document.getElementById("show_$i").getElementsByTag("li")
                        arr = ArrayList()
                        for (d in 0 until shows.size) {
                            when (i) {
                                1, 5, 6, 7 -> {
                                    //最新帖
                                    //本周热帖
                                    //随机精华
                                    //招聘
                                    val category = shows[d].getElementsByTag("font").text()
                                    val b = shows[d].getElementsByTag("span")
                                    val c = b[0].getElementsByTag("a")
                                    val userhref = c.attr("href")
                                    val title = shows[d].getElementsByTag("a")[1].getElementsByTag("a").attr("title")
                                    val tidHref = shows[d].getElementsByTag("a")[1].getElementsByTag("a").attr("href")
                                    arr.add(mNewsModel().apply {
                                        this.category = category
                                        this.info = getInfos(title)
                                        this.userhref = userhref
                                        this.tidHref = tidHref
                                    })
                                }
                                2 -> {
                                    //最新定制
                                    val money = shows[d].getElementsByClass("sprice").text()
                                    val category = shows[d].getElementsByTag("font").text()
                                    val c = shows[d].getElementsByTag("a")
                                    val href = c.attr("href")
                                    val title = c.attr("title")
                                    val tidHref = shows[d].getElementsByTag("a")[0].getElementsByTag("a").attr("href")
                                    arr.add(mNewsModel().apply {
                                        this.category = category
                                        info = getInfos(title)
                                        this.userhref = href
                                        this.tidHref = tidHref
                                        this.money = money
                                    })
                                }
                                3 -> {
                                    //易语言TV
                                    val sendtime = shows[d].getElementsByTag("a")[0].text()
                                    val href = shows[d].getElementsByTag("a")[0].attr("href")
                                    val tit = shows[d].getElementsByTag("a")[1].attr("title")
                                    val title = shows[d].getElementsByTag("a")[1].text()
                                    val author = tit.substring(tit.indexOf("发布者：") + "发布者：".length, tit.indexOf(" ", tit.indexOf("发布者：") + "发布者：".length))
                                    arr.add(mNewsModel().apply {
                                        this.category = "易语言TV"
                                        info = mNewsModel.mInfo().apply {
                                            this.title = title
                                            this.sendof = sendtime
                                            this.postname = author
                                        }
                                        //此处视频地址 自动对接YYYTV，故无用户信息
                                        this.tidHref = href
                                        this.userhref = href
                                    })
                                }
                            }
                        }
                        mainList.add(arr)
                    }
                    arr = ArrayList()
                    //后面用于方便排序显示，暂时不做。
                    arr.addAll(mainList[0])
                    arr.addAll(mainList[1])
                    arr.addAll(mainList[2])
                    arr.addAll(mainList[3])
                    arr.addAll(mainList[4])
                    arr.addAll(mainList[5])
                    GetData.getDataSuccess(arr)
                }
            }))
        }

        fun getBBSPageInfo(url: String, get: BBSPageInfoGet) {
            val s = userInfos("0", "0", "https://bbs.125.la:443/uc_server/images/noavatar_middle.gif", "https://bbs.125.la:443/uc_server/images/noavatar_middle.gif")
            mSharedContext.getQueue().add(HttpHelper.Builder(url).Build(object : HttpHelper.Callback {
                override fun NetResult(isSuccess: Boolean, retData: String) {
                    if (!isSuccess) {
                        get.getSuccess(-1, s)
                        return
                    }
                    if (retData.indexOf("请先登录后才能继续浏览") != -1) {
                        get.getSuccess(-2, s)
                    } else {
                        try {
                            val doc = Jsoup.parse(retData)
                            with(doc.getElementById("postlist").getElementsByClass("hm ptn")[0].getElementsByClass("xi1")) {
                                val watch = this[0].text()
                                s.watch = watch
                                val repost = this[1].text()
                                s.repost = repost
                            }
                            val img = doc.getElementsByClass("avtm")[0].getElementsByTag("img").attr("src")
                            s.imgUrl = img
                            get.getSuccess(0, s)
                        } catch (e: Exception) {
                            println(e.message)
                            get.getSuccess(-3, s)
                        }
                    }
                }
            }))
        }

        class userInfos(var watch: String, var repost: String, var imgUrl: String, var onerror: String)

        interface BBSPageInfoGet {
            /**
             * 获取数据成功回调
             * @param status 状态 0=正常 -1 = 失败  -2 = 无权限查看本帖
             */
            fun getSuccess(status: Int, ret: userInfos)
        }

        //最新主题解析
        fun getInfos(str: String): mNewsModel.mInfo {
            val p = Pattern.compile("标题：(.*?)\\n楼主：(.*?)发表于：(.*?)\\n")
            val m = p.matcher(str)
            if (m.find()) {
                //标题
                val tit = m.group(1)
                //作者
                val master = m.group(2).replace("&nbsp", "").trim()
                //发布时间
                val postof = m.group(3)
                return mNewsModel.mInfo().apply {
                    this.postname = master
                    this.sendof = postof
                    this.title = tit
                }
            } else return mNewsModel.mInfo()
        }

        //str.substring(str.indexOf("<!--第1格：最新主题-->",0),str.indexOf("<!--第2格 最新担保-->"))
        interface GetData {
            fun getDataSuccess(str: ArrayList<mNewsModel>)
        }

        class QRBack {
            /**
             * status : 1
             * data :
             */
            var status: String? = null
            var data: String? = null
        }

        interface QRCallBack {
            fun getImgSuccess(status: Int, bit: Bitmap?)
        }


        /**
         * 正则表达式取中间文本
         */
        private fun getSubString(all: String, Regexs: String): String {
            val p = Pattern.compile(Regexs)
            val m = p.matcher(all)
            //m.group(0) 代表匹配到的整条数据
            if (m.find()) {
                return m.group(1)
            }
            return ""
        }
    }

    interface TaskCallBack {
        fun taskCallback(a: ArrayList<mTask>)
        fun taskGetFailed()
        fun taskOver()
    }
}


