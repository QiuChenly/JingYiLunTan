package com.qiuchen.UI.Fragment;

import com.qiuchen.Base.BaseFragment;
import com.qiuchen.Datas.NewsData;
import com.qiuchen.Models.NewsModel;
import com.qiuchen.R;
import com.qiuchen.Views.NewsView;

import org.jetbrains.annotations.NotNull;

public class NewsFragment extends BaseFragment<NewsView, NewsModel, NewsData> implements NewsView {
    @Override
    public int getLayout() {
        return R.layout.fragment_newslayout;
    }

    @Override
    public void onCreated() {

    }

    @NotNull
    @Override
    public NewsView createView() {
        return this;
    }

    @NotNull
    @Override
    public NewsData createPresenter() {
        return new NewsData();
    }

    @Override
    public void getData(@NotNull String str) {

    }
}
