package com.qiuchen.Utils

import com.qiuchen.DataModel.mTask
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList

/**
 * Created by qiuchen on 2018/1/1.
 */
class mUtils {
    companion object {
        var size = 0
        fun getCanScroll(): Boolean {
            return size > 15
        }

        fun orders(list: ArrayList<mTask>): ArrayList<mTask> {
            val dates = SimpleDateFormat("yyyy-MM-dd hh:mm")

            for (a in list.indices) {
                for (b in 0 until a) {
                    try {
                        if (dates.parse(list[a].发布时间).time > dates.parse(list[b].发布时间).time) {
                            val temp = list[a]
                            list[a] = list[b]
                            list[b] = temp
                        }
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
            }
            return list
        }
        lateinit var mDataBaseHelper: mDataBaseHelper
    }
}