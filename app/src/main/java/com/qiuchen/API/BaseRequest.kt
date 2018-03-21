package com.qiuchen.API

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.qiuchen.mSharedContext

/**
 * @author QiuChenLuoYe 在 2018/3/21 下午10:31.
 * @since
 */
class BaseRequest(var url: String) : Thread() {

    fun setURL(url: String): BaseRequest {
        this.url = url
        return this
    }

    private var data: String = ""

    private var method: String = ""

    fun setData(data: String): BaseRequest {
        this.data = data
        this.method = "POST"
        return this
    }

    private var cook: String = ""

    fun setCookie(cook: String): BaseRequest {
        this.cook = cook
        return this
    }

    private var mRequestCallBack: BaseRequest.RequestCallBack? = null

    fun Request(RequestCallBack: RequestCallBack) {
        this.mRequestCallBack = RequestCallBack
        start()
    }

    private var mList: MutableMap<String, String> = HashMap()
    fun setHeader(key: String, value: String): BaseRequest {
        mList[key] = value
        return this
    }

    interface RequestCallBack {
        fun onSuccess(it: String)
        fun onFailed(Reason: String)
    }

    override fun run() {
        if (url.isNotEmpty()) {
            val req: StringRequest = if (method == "POST") {
                object : StringRequest(com.android.volley.Request.Method.POST, url,
                        Response.Listener {
                            mRequestCallBack?.onSuccess(it)
                        }, Response.ErrorListener {
                    mRequestCallBack?.onFailed(it.message!!)
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        mList["Cookie"] = cook
                        return mList
                    }

                    override fun getParams(): MutableMap<String, String> {
                        val mReq: MutableMap<kotlin.String, kotlin.String> = HashMap()
                        data.split("&")
                                .forEach {
                                    val a = it.split("=")
                                    mReq[a[0]] = a[1]
                                }
                        return mReq
                    }

                }
            } else {
                object : StringRequest(url, Response.Listener {
                    mRequestCallBack?.onSuccess(it)
                }, Response.ErrorListener {
                    mRequestCallBack?.onFailed(it.message!!)
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        mList["Cookie"] = cook
                        return mList
                    }

                    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                        val rets = response?.headers?.get("Set-Cookie")
                        println(rets)
                        return super.parseNetworkResponse(response)
                    }
                }
            }
            mSharedContext.getQueue().add(req)
        } else mRequestCallBack?.onFailed("没有设置网址！")
    }
}