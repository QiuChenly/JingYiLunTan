package com.qiuchen.UI.Fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.magiepooh.recycleritemdecoration.ItemDecorations;
import com.qiuchen.Adapter.NewsContentAdapter;
import com.qiuchen.Base.BaseFragment;
import com.qiuchen.DataModel.mNewsModel;
import com.qiuchen.Datas.NewsData;
import com.qiuchen.Models.NewsModel;
import com.qiuchen.R;
import com.qiuchen.Views.NewsView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment<NewsView, NewsModel, NewsData> implements NewsView, SwipeRefreshLayout.OnRefreshListener {

    @Override
    public int getLayout() {
        return R.layout.fragment_newslayout;
    }

    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    public void onCreated() {
        RecyclerView rv_fragment_rv = (RecyclerView) getView().findViewById(R.id.rv_fragment_rv);
        rv_fragment_rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_fragment_rv.setItemAnimator(new DefaultItemAnimator());
        rv_fragment_rv.setHasFixedSize(false);
        RecyclerView.ItemDecoration decoration = ItemDecorations.vertical(this.getContext())
                .type(1, R.drawable.recyclerview_splitline)
                .type(2, R.drawable.recyclerview_splitline)
                .create();
        rv_fragment_rv.addItemDecoration(decoration);
        decoration = null;
        mSwipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.mSwipeRefresh);
        mSwipeRefresh.setOnRefreshListener(this);

        //show with Top Pages.
        ArrayList<String> ll2 = new ArrayList<>();
        ll2.add("http://pic35.photophoto.cn/20150516/0017030235042021_b.jpg");
        ll2.add("http://pic36.photophoto.cn/20150721/0020033047628193_b.jpg");
        ll2.add("http://pic107.nipic.com/file/20160820/4083096_103334318495_2.jpg");
        ll2.add("http://www.liandu.gov.cn/zhxx/rdzt/ldxf/hdzl/cjqg/201610/W020161017574715226738.jpg");
        ll2.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524125099962&di=c2ca6454dd50f6f9b3ee4f7f36f647e8&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F18%2F50%2F03%2F62R58PICig9_1024.jpg");
        ada = new NewsContentAdapter(new ArrayList<mNewsModel>(), ll2, this);
        ll2 = null;
        rv_fragment_rv.setAdapter(ada);
        ada.startLoop();
        getPres().getData();
    }

    NewsContentAdapter ada;

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
    public void onShowPageBeClick(@NotNull String str) {

    }

    Handler h = new Handler();

    @Override
    public void getDataSuccess(@NotNull final ArrayList<mNewsModel> Str) {
        h.post(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
                if (Str.size() > 0) {

                    ada.setList(Str);
                    ada.notifyDataSetChanged();
                    Toast.makeText(getContext(), "已刷新数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "网络请求失败 ^_^", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getPres().getData();
    }
}
