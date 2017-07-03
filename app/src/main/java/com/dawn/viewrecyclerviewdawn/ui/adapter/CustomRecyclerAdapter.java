package com.dawn.viewrecyclerviewdawn.ui.adapter;

import android.support.annotation.Nullable;

import com.dawn.viewrecyclerviewdawn.R;
import com.dawn.viewrecyclerviewdawn.model.ItemRecycler;
import com.dawn.viewrecyclerviewdawn.recycler.BaseQuickAdapter;
import com.dawn.viewrecyclerviewdawn.recycler.BaseViewHolder;

import java.util.List;

/**
 * Created by 90449 on 2017/7/3.
 */

public class CustomRecyclerAdapter extends BaseQuickAdapter<ItemRecycler, BaseViewHolder> {
    public CustomRecyclerAdapter(@Nullable List<ItemRecycler> data) {
        super(R.layout.item_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemRecycler item) {
        helper.setText(R.id.tv_num, "num : " + item.getId());
        helper.setText(R.id.tv_name, "name : " + item.getName());
        helper.addOnClickListener(R.id.line_main);
    }

}
