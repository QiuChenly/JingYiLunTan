package com.qiuchen.DataModel;

import org.jetbrains.annotations.Nullable;

/**
 * 用于最新消息的数据模型
 */
public class mNewsModel {

    public String
    /**
     * 帖子类别
     */
    category;
    public mInfo info;
    @Nullable
    public String money;
    @Nullable
    public String tidHref;
    @Nullable
    public String userhref;

    public static class mInfo {
        public String
                /**
                 * 发帖人ID
                 */
                postname,
        /**
         * 帖子标题
         */
        title,
        /**
         * 发帖时间
         */
        sendof;
    }
}
