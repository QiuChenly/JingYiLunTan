package com.qiuchen.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qiuchen.DataModel.mTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by qiuchen on 2018/1/2.
 */

public class mDataBaseHelper extends SQLiteOpenHelper {
    public mDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    String DBname_History = "history";

    private String CREATE_HISTORY_DATABASE =
            "create table if not exists " + DBname_History + "(id integer primary key autoincrement,订单帖子标题 TEXT, 预算价格 TEXT, 是否要源码 TEXT, 发布时间 TEXT, 结束日期 TEXT, 下单方 TEXT, 联系下单方 TEXT, url TEXT, 下单方Url TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_HISTORY_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addItem(mTask mTasks) {
        String exec = "insert into " + DBname_History + "(订单帖子标题, 预算价格, 是否要源码, 发布时间, 结束日期, 下单方, 联系下单方, url, 下单方Url)values('" + mTasks.订单帖子标题 + "','" + mTasks.预算价格 + "','" + mTasks.是否要源码 + "','" + mTasks.发布时间 + "','" + mTasks.结束日期 + "','" + mTasks.下单方 + "','" + mTasks.联系下单方 + "','" + mTasks.url + "','" + mTasks.下单方Url + "');";
        getWritableDatabase().execSQL(exec);
    }

    public ArrayList<mTask> getAllHistory() {
        String exec = "select * from " + DBname_History;
        ArrayList<mTask> mList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery(exec, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                mTask mTasks = new mTask();
                mTasks.订单帖子标题 = cursor.getString(1);
                mTasks.预算价格 = cursor.getString(2);
                mTasks.是否要源码 = cursor.getString(3);
                mTasks.发布时间 = cursor.getString(4);
                mTasks.结束日期 = cursor.getString(5);
                mTasks.下单方 = cursor.getString(6);
                mTasks.联系下单方 = cursor.getString(7);
                mTasks.url = cursor.getString(8);
                mTasks.下单方Url = cursor.getString(9);
                mList.add(mTasks);
                if (cursor.isLast()) {
                    break;
                }
            }
        }
        return mUtils.Companion.orders(mList);
    }


}
