package com.dawn.viewrecyclerviewdawn;

import com.dawn.viewrecyclerviewdawn.recycler.loadmore.LoadMoreView;

/**
 * Created by 90449 on 2017/7/3.
 */

public final class CustomLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
