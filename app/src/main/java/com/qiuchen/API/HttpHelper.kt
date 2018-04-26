package com.qiuchen.API

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.qiuchen.mSharedContext

class HttpHelper {

    fun execute(req: Request<String>) {
        mSharedContext.getQueue().add(req)
    }

    class Builder(url: String) {
        private var u = url
        private var DataS = HashMap<String, String>()
        fun setData(Str: String): Builder {
            Str.split("&").forEach {
                if (it.indexOf("=") == -1) return@forEach
                val s = it.split("=")
                DataS[s[0]] = s[1]
            }
            return this
        }

        private var allow302 = false
        fun set302(allow302: Boolean): Builder {
            this.allow302 = allow302
            return this
        }

        fun setData(key: String, value: String): Builder {
            DataS[key] = value
            return this
        }

        fun Build(cb: Callback): StringRequest {
            return object : StringRequest(if (DataS.size > 0) Request.Method.POST else Request.Method.GET, u, Response.Listener<String> {
                cb.NetResult(true, it)
            }, Response.ErrorListener {
                cb.NetResult(false, "")
            }) {
                override fun getParams(): MutableMap<String, String> {
                    return DataS
                }

                override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                    val s = response?.headers?.get("Set-Cookie")
                    return super.parseNetworkResponse(response)
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>().apply {
                        this["Accept"] = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
                        this["Accept-Language"] = "zh-CN,zh;q=0.9,en;q=0.8"
                        this["Cache-Control"] = "no-cache"
                        this["Connection"] = "keep-alive"
                        this["Host"] = "bbs.125.la"
                        this["User-Agent"] = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36"
                        this["Cookie"] = mSharedContext.getCookie().toString()
                    }
                }
            }
        }
    }

    interface Callback {
        fun NetResult(isSuccess: Boolean, retData: String)
    }
}