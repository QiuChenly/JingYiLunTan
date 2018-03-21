package com.qiuchen.Models

import com.qiuchen.API.BaseRequest
import com.qiuchen.API.mJingYi
import com.qiuchen.Base.BaseModel
import com.qiuchen.iModels.iForumOrder
import com.qiuchen.mSharedContext

/**
 * @author QiuChenLuoYe 在 2018/3/19 下午3:13.
 * @since
 */
class ForumModel : BaseModel(), iForumOrder {
    override fun getList(req: BaseRequest.RequestCallBack) {
        val u = mJingYi._TASK_LIST + System.currentTimeMillis()
        BaseRequest(u)
                .setCookie(mSharedContext.getCookie().toString())
                .setHeader("X-Requested-With", "XMLHttpRequest")
                .Request(req)
    }
}