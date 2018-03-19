package com.qiuchen.Base

import android.os.Handler
import android.os.Looper

/**
 * @author QiuChenLuoYe 2018年03月18日 18时22分 创建.
 * @since
 */
abstract class BasePresenter<V : BaseView, M : BaseModel> {

    private var mView: V? = null
    private var model: M? = null
    private val hand = Handler(Looper.getMainLooper())

    abstract fun createModel(): M?

    fun attach(view: V) {
        mView = view
        model = createModel()
    }

    fun detach() {
        mView = null
        model = null
    }

    fun post(run: Runnable) {
        post(run, 0L)
    }

    fun post(run: Runnable, delay: Long) {
        hand.postDelayed(run, delay)
    }

    fun getView() = mView
    fun getModel(): M? {
        if (model == null) model = createModel()
        return model
    }
}
