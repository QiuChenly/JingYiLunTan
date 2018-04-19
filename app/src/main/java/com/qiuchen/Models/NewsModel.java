package com.qiuchen.Models;

import com.qiuchen.API.mJingYi;
import com.qiuchen.Base.BaseModel;

public class NewsModel extends BaseModel {

    public void getMainPageDatas(final mJingYi.Companion.GetData Get) {
        //懒得写线程池，后面请求多了再说
        new Thread() {
            @Override
            public void run() {
                mJingYi.Companion.getMainPageData(Get);
            }
        }.start();
    }
}
