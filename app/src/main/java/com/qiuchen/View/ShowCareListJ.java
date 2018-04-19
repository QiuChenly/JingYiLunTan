package com.qiuchen.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qiuchen.Adapter.DanZiAdapter;
import com.qiuchen.DataModel.mTask;
import com.qiuchen.R;
import com.qiuchen.Utils.mUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by qiuchen on 2018/1/1.
 */

public class ShowCareListJ implements DanZiAdapter.onItemClick, SwipeRefreshLayout.OnRefreshListener {
    public ShowCareListJ(View v, iGetMoreData moreData, Context context) {
        this.moreData = moreData;
        mView = (RecyclerView) v.findViewById(R.id.mTaskListRV);
        mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.mSwipeRefresh);
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLACK);
        mSwipeRefresh.setOnRefreshListener(this);
        mView.setHasFixedSize(false);
        mView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        mTaskListAdapter = new DanZiAdapter(mUtils.mDataBaseHelper.getAllHistory(), this, context);
        mView.setAdapter(mTaskListAdapter);
    }

    private iGetMoreData moreData;
    private RecyclerView mView;
    private DanZiAdapter mTaskListAdapter;
    private SwipeRefreshLayout mSwipeRefresh;

    public void addDatas(ArrayList<mTask> mTasks) {
        mTaskListAdapter.addData(mTasks, mView);
    }

    @Override
    public void onTaskItemBeClick(int position, @NotNull View view) {
        //弃用方法，此回调不再使用
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
