package com.qiuchen.Base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

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

    fun Fragment.show(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }


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

    override fun onDestroyView() {
        if (view != null && mPres != null) mPres?.detach()
        super.onDestroyView()
    }

    fun getPres(): P? {
        return mPres
    }
}
