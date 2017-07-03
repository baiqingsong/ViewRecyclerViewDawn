package com.dawn.viewrecyclerviewdawn.ui.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dawn.viewrecyclerviewdawn.CustomLoadMoreView;
import com.dawn.viewrecyclerviewdawn.R;
import com.dawn.viewrecyclerviewdawn.model.ItemRecycler;
import com.dawn.viewrecyclerviewdawn.recycler.BaseQuickAdapter;
import com.dawn.viewrecyclerviewdawn.ui.adapter.CustomRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 90449 on 2017/7/3.
 */

public class PullRefreshRecyclerActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private CustomRecyclerAdapter mAdapter;
    private List<ItemRecycler> mData;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private static int page_size = 6;
    private int delayMillis = 1000;
    private int current_page = 1;

    @Override
    protected int setView() {
        return R.layout.activity_pull_refresh_recycler;
    }

    @Override
    protected void initData() {
        initRecyclerData();
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
    }

    @Override
    protected void initListener() {
        initRecyclerView();
        initAdapter();
        recyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setEnableLoadMore(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        current_page = 1;
                        initRecyclerData();
                        mAdapter.setNewData(mData);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.setEnableLoadMore(true);
                    }
                }, delayMillis);
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
    }
    private void initRecyclerData(){
        mData = new ArrayList<>();
        for(int i = 0; i < 10; i ++){
            ItemRecycler itemRecycler = new ItemRecycler(i + 1, "dawn " + i);
            mData.add(itemRecycler);
        }
    }
    private List<ItemRecycler> pullRecyclerData(){
        mData = new ArrayList<>();
        for(int i = 10 * (current_page - 1); i < 10 * current_page; i ++){
            ItemRecycler itemRecycler = new ItemRecycler(i + 1, "dawn " + i);
            mData.add(itemRecycler);
        }
        return mData;
    }
    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
    }
    private void initAdapter(){
        mAdapter = new CustomRecyclerAdapter(mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mSwipeRefreshLayout.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        current_page ++;
                        if(current_page > page_size){
                            mAdapter.loadMoreEnd(false);
                            mSwipeRefreshLayout.setEnabled(true);
                        }else{
                            pullRecyclerData();
                            mAdapter.addData(pullRecyclerData());
                            mAdapter.loadMoreComplete();
                            mSwipeRefreshLayout.setEnabled(true);
                        }
                    }
                }, delayMillis);

            }
        }, recyclerView);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
    }
}
