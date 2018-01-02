package com.qiuchen.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiuchen.Adapter.mTaskListAdapter;
import com.qiuchen.DataModel.mTask;
import com.qiuchen.R;
import com.qiuchen.Utils.mUtils;
import com.qiuchen.View.iGetMoreData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuchen on 2018/1/1.
 */

public class ShowCareListJ implements mTaskListAdapter.onItemClick, SwipeRefreshLayout.OnRefreshListener {
    public ShowCareListJ(View v, iGetMoreData moreData,Context context) {
        this.moreData = moreData;
        mView = v.findViewById(R.id.mTaskListRV);
        mSwipeRefresh = v.findViewById(R.id.mSwipeRefresh);
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLACK);
        mSwipeRefresh.setOnRefreshListener(this);
        mView.setHasFixedSize(false);
        mView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        mTaskListAdapter = new mTaskListAdapter(mUtils.mDataBaseHelper.getAllHistory(), this,context);
        mView.setAdapter(mTaskListAdapter);
    }

    private iGetMoreData moreData;
    private RecyclerView mView;
    private mTaskListAdapter mTaskListAdapter;
    private SwipeRefreshLayout mSwipeRefresh;

    public void addDatas(ArrayList<mTask> mTasks) {
        mTaskListAdapter.addData(mTasks, mView);
    }

    @Override
    public void onTaskItemBeClick(int position, @NotNull View view) {

    }


    @Override
    public void onRefresh() {
        moreData.getMoreData();
    }

    public SwipeRefreshLayout getRefresh() {
        return mSwipeRefresh;
    }

    public int getCount() {
        return mTaskListAdapter.getItemCount();
    }
}
