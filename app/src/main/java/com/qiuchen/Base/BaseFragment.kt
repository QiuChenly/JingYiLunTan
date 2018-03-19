package com.qiuchen.Base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author QiuChenLuoYe 2018年03月18日 18时11分 创建.
 * @since
 */
abstract class BaseFragment<V : BaseView, M : BaseModel, P : BasePresenter<V, M>> : Fragment() {

    abstract fun getLayout(): Int
    abstract fun onCreated()
    abstract fun createView(): V
    abstract fun createPresenter(): P

    private var mView: V? = null
    private var mPres: P? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = createView()
        mPres = createPresenter()
        if (mView != null) mPres?.attach(mView!!)
        onCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view != null && mPres != null) mPres?.detach()
    }

    fun getPres(): P? {
        return mPres
    }
}
