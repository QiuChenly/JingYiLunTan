package com.qiuchen.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.qiuchen.Base.BaseVH
import com.qiuchen.R

class NewsContentAdapter(var mlist: List<String>) : RecyclerView.Adapter<BaseVH>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent?.context).inflate(when (viewType) {
            SPECIAL_VIEWPAGERANDHEAD -> R.layout.item_special_viewpagerandhead
            else -> R.layout.item_special_viewpagerandhead
        }, parent, false))
    }

    companion object {
        private const val SPECIAL_VIEWPAGERANDHEAD = 1;
        private const val SPECIAL_NORMAL = 1;
    }

    //special view first
    override fun getItemViewType(position: Int) = if (position == 0) SPECIAL_VIEWPAGERANDHEAD else SPECIAL_NORMAL
    override fun getItemCount() = mlist.size
    override fun onBindViewHolder(holder: BaseVH, position: Int) {

    }
}