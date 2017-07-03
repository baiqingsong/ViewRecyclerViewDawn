package com.dawn.viewrecyclerviewdawn.ui.activity;

import android.view.View;

import com.dawn.viewrecyclerviewdawn.R;

public class MainActivity extends BaseActivity {

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    protected void jumpToAnimationRecycler(View view){
        jumpTo(AnimationRecyclerActivity.class);
    }
    protected void jumpToHeaderFooterRecycler(View view){
        jumpTo(HeaderFooterRecyclerActivity.class);
    }
    protected void jumpToPullRefreshRecycler(View view){
        jumpTo(PullRefreshRecyclerActivity.class);
    }
    protected void jumpToEmptyRecycler(View view){
        jumpTo(EmptyRecyclerActivity.class);
    }
    protected void jumpToExpandableRecycler(View view){
        jumpTo(ExpandableRecyclerActivity.class);
    }
}
