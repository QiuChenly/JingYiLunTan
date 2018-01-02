package com.qiuchen.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiuchen.R
import kotlinx.android.synthetic.main.navigation_item.view.*

/**
 * Created by qiuchen on 2018/1/1.
 */
class navigationBarList(var mList: List<Map<Int, String>>,
                        var onItemClicks: onItemClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {
    override fun onClick(p0: View) {
        onItemClicks.onItemClick(p0.tag as Int)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mList[position]
        for (items in item.entries) {
            with(holder.itemView) {
                this.tag = position
                this.mItemImage.setImageResource(items.key)
                this.mItemTitle.text = items.value

            }
        }
        holder.itemView.setOnClickListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val a = LayoutInflater.from(parent.context).inflate(R.layout.navigation_item, parent, false)
        return VH(a)
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    interface onItemClick {
        fun onItemClick(position: Int)
    }

    class VH(v: View) : RecyclerView.ViewHolder(v)
}