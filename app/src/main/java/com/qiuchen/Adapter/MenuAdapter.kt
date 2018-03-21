package com.qiuchen.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qiuchen.Base.BaseVH
import com.qiuchen.Beans.MenuBean
import com.qiuchen.R
import kotlinx.android.synthetic.main.item_menu.view.*

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午2:25.
 * @since
 */
class MenuAdapter(mList: List<MenuBean>, mCallback: MenuItemCallback) : RecyclerView.Adapter<BaseVH>() {
    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        with(holder.itemView!!) {
            setOnClickListener {
                mCallback!!.onClick(position, mList!![position].title)
            }
            menu_title.text = mList!![position].title
            menu_icon.setImageResource(mList!![position].icon)
        }
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return BaseVH(item)
    }

    private var mList: List<MenuBean>? = mList
    private var mCallback: MenuItemCallback? = mCallback

    interface MenuItemCallback {
        fun onClick(position: Int, title: String)
    }
}