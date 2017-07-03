package com.dawn.viewrecyclerviewdawn.ui.adapter;

import android.view.View;
import android.widget.Toast;

import com.dawn.viewrecyclerviewdawn.R;
import com.dawn.viewrecyclerviewdawn.model.ExplandableModel1;
import com.dawn.viewrecyclerviewdawn.model.ExplandableModel2;
import com.dawn.viewrecyclerviewdawn.recycler.BaseMultiItemQuickAdapter;
import com.dawn.viewrecyclerviewdawn.recycler.BaseViewHolder;
import com.dawn.viewrecyclerviewdawn.recycler.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by 90449 on 2017/7/3.
 */

public class ExpandableRecyclerAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableRecyclerAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_1);
        addItemType(TYPE_LEVEL_2, R.layout.item_expandable_2);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_1:
                helper.setText(R.id.tv_num, "num : " + ((ExplandableModel1)item).getId());
                helper.setText(R.id.tv_name, "name : " + ((ExplandableModel1)item).getName());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (((ExplandableModel1)item).isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_2:
                helper.setText(R.id.tv_num, "num : " + ((ExplandableModel2)item).getId());
                helper.setText(R.id.tv_name, "name : " + ((ExplandableModel2)item).getName());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, ((ExplandableModel2)item).getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
