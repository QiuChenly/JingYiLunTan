package com.qiuchen.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.qiuchen.Beans.MenuBean
import com.qiuchen.R

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午2:25.
 * @since
 */
class MenuAdapter(mList: List<MenuBean>, mCallback: MenuItemCallback) : BaseAdapter() {
    private var mList: List<MenuBean>? = mList
    private var mCallback: MenuItemCallback? = mCallback

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var item = convertView
        if (item == null) item = LayoutInflater.from(parent?.context).inflate(R.layout.item_menu, parent, false)

        //bind click
        with(item!!) {
            setOnClickListener {
                mCallback!!.onClick(position)
            }
        }
        return item
    }

    override fun getItem(position: Int): Any {
        return mList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mList!!.size
    }

    interface MenuItemCallback {
        fun onClick(position: Int)
    }
}