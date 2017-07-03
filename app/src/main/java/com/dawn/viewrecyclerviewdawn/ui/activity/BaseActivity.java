package com.dawn.viewrecyclerviewdawn.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by 90449 on 2017/7/3.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(setView());
        initView();
        initListener();
    }
    protected abstract @LayoutRes int setView();
    protected abstract void initData();
    protected abstract void initView();
    protected abstract void initListener();
    protected void jumpTo(Class<?> mClass){
        startActivity(new Intent(this, mClass));
    }
    protected void toastUI(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, (msg == null || msg.trim().length() == 0) ? "" : msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
