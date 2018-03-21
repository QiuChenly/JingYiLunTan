package com.qiuchen.iModels

import com.qiuchen.API.BaseRequest

/**
 * @author QiuChenLuoYe 在 2018/3/21 下午9:42.
 * @since
 */
interface iForumOrder {
    fun getList(req: BaseRequest.RequestCallBack)
}