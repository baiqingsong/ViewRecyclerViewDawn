package com.dawn.viewrecyclerviewdawn.model;

import com.dawn.viewrecyclerviewdawn.recycler.entity.MultiItemEntity;
import com.dawn.viewrecyclerviewdawn.ui.adapter.ExpandableRecyclerAdapter;

/**
 * Created by 90449 on 2017/7/3.
 */

public class ExplandableModel2 implements MultiItemEntity {
    private int id;
    private String name;

    public ExplandableModel2(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getItemType() {
        return ExpandableRecyclerAdapter.TYPE_LEVEL_2;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
