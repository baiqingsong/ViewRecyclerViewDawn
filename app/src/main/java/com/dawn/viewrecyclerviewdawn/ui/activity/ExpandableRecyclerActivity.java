package com.dawn.viewrecyclerviewdawn.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dawn.viewrecyclerviewdawn.R;
import com.dawn.viewrecyclerviewdawn.model.ExplandableModel1;
import com.dawn.viewrecyclerviewdawn.model.ExplandableModel2;
import com.dawn.viewrecyclerviewdawn.recycler.entity.MultiItemEntity;
import com.dawn.viewrecyclerviewdawn.ui.adapter.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 90449 on 2017/7/3.
 */

public class ExpandableRecyclerActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ExpandableRecyclerAdapter mAdapter;
    private List<MultiItemEntity> mData;
    @Override
    protected int setView() {
        return R.layout.activity_expandable_recycler;
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for(int i = 0; i < 5; i ++){
            ExplandableModel1 explandableModel1 = new ExplandableModel1(i + 1, "dawn " + i);
            for(int j = 0; j < 2; j ++){
                ExplandableModel2 explandableModel2 = new ExplandableModel2(j + 1, "dawn " + i + " sub dawn " + j);
                explandableModel1.addSubItem(explandableModel2);
            }
            mData.add(explandableModel1);
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
    }
    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initAdapter(){
        mAdapter = new ExpandableRecyclerAdapter(mData);
        mAdapter.expandAll();
    }
}
