package com.dawn.viewrecyclerviewdawn.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.dawn.viewrecyclerviewdawn.R;
import com.dawn.viewrecyclerviewdawn.model.ItemRecycler;
import com.dawn.viewrecyclerviewdawn.recycler.BaseQuickAdapter;
import com.dawn.viewrecyclerviewdawn.ui.adapter.CustomRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 90449 on 2017/7/3.
 */

public class AnimationRecyclerActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private CustomRecyclerAdapter mAdapter;
    private List<ItemRecycler> mData;
    @Override
    protected int setView() {
        return R.layout.activity_animation_recycler;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for(int i = 0; i < 50; i ++){
            ItemRecycler itemRecycler = new ItemRecycler(i + 1, "dawn " + i);
            mData.add(itemRecycler);
        }
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    protected void initListener() {
        initRecyclerView();
        initAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ItemRecycler itemRecycler = ((List<ItemRecycler>) adapter.getData()).get(position);
                LinearLayout lineMain = (LinearLayout) view.findViewById(R.id.line_main);
                lineMain.setBackgroundColor(Color.RED);
                toastUI(itemRecycler.getName());
            }
        });
    }
    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
    }
    private void initAdapter(){
        mAdapter = new CustomRecyclerAdapter(mData);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }
}
