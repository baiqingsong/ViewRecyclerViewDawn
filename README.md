# recyclerView的使用

* [recyclerView引用](#recyclerview引用)
* [recyclerView相关类导入](#recyclerview相关类导入)
* [recyclerView使用](#recyclerview使用)
    * [recyclerView在xml使用](#recyclerview在xml使用)
    * [adapter调用](#adapter调用)
    * [recyclerView设置](#recyclerview设置)
        * [LinearLayoutManager](#linearlayoutmanager)
        * [GridLayoutManager](#gridlayoutmanager)
    * [adapter设置](#adapter设置)
        * [openLoadAnimation](#openloadanimation)
        * [setOnItemChildClickListener](#setonitemchildclicklistener)
        * [addHeaderView](#addheaderview)
        * [addFooterView](#addfooterview)
        * [SwipeRefreshLayout](#swiperefreshlayout)
        * [setOnLoadMoreListener](#setonloadmorelistener)
        * [setLoadMoreView](#setloadmoreview)
        * [setEmptyView](#setemptyview)
    * [ExpandableRecycler使用](#expandablerecycler使用)
        * [Model](#model)
        * [adapter](#adapter)
        * [activity里面配置](#activity里面配置)
* [参考网站](#参考网站)


## recyclerView引用

app的build.gradle里面添加：
```
dependencies {
    compile 'com.android.support:recyclerview-v7:25.3.1'
}
```

## recyclerView相关类导入

在recycler包下所有类复制进入app项目
[recyclerView控件](app/src/main/assets/auto_view.png recyclerView控件)


错误调整

    1.每个类引用的包名称不同
    2.复制资源文件下ids.xml中的相关内容
    3.复制资源文件layout里面quick_view_load_more.xml
    4.复制资源文件下dimens.xml中的相关内容
    5.复制资源文件下strings.xml中的相关内容
    
## recyclerView使用

#### recyclerView在xml使用
RecyclerView在xml引用：
```
<android.support.v7.widget.RecyclerView
    android:id="@+id/recycler_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

#### adapter调用
普通recyclerView对应的adapter
```
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
```

其中继承的BaseQuickAdapter的两个参数中，第一个参数是item对应的实体类
主要是实现convert这个方法，helper包含一些方法，可以进行文字添加，图片添加，添加点击事件等等。
item是对应的实体类。

#### recyclerView设置

recyclerView必须设置setLayoutManager这个方法
```
recyclerView.setLayoutManager(new LinearLayoutManager(this));
```
recyclerView的显示模式

###### LinearLayoutManager

这个类有三个参数：
第一个是context，
第二个是布局(LinearLayoutManager.HORIZONTAL和LinearLayoutManager.VERTICAL),
第三个参数是是否反转true或者false

###### GridLayoutManager

这个类有两个参数：
第一个参数是context
第二个参数显示多少列
第三个参数代表布局（GridLayoutManager.HORIZONTAL和GridLayoutManager.VERTICAL）
第四个参数代表是否反转

#### adapter设置

###### openLoadAnimation
openLoadAnimation这个代表动画显示
```
mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
```

其中显示动画还有
BaseQuickAdapter.ALPHAIN
BaseQuickAdapter.SCALEIN
BaseQuickAdapter.SLIDEIN_BOTTOM
BaseQuickAdapter.SLIDEIN_LEFT
BaseQuickAdapter.SLIDEIN_RIGHT

###### setOnItemChildClickListener
adapter可以设置点击事件，但是对应的adapter类中要添加对应的addOnClickListener事件
```
mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ItemRecycler itemRecycler = ((List<ItemRecycler>) adapter.getData()).get(position);
        LinearLayout lineMain = (LinearLayout) view.findViewById(R.id.line_main);
        lineMain.setBackgroundColor(Color.RED);
        toastUI(itemRecycler.getName());
    }
});
```
```
@Override
protected void convert(BaseViewHolder helper, ItemRecycler item) {
    helper.setText(R.id.tv_num, "num : " + item.getId());
    helper.setText(R.id.tv_name, "name : " + item.getName());
    helper.addOnClickListener(R.id.line_main);
}
```

###### addHeaderView
设置recyclerView的头View
```
mAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header, null), 0);
```

###### addFooterView
设置recyclerView的脚View
```
mAdapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_footer, null), 0);
```

###### SwipeRefreshLayout
刷新需要SwipeRefreshLayout辅助
```
<android.support.v4.widget.SwipeRefreshLayout
    android:id="@+id/swipeLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >
    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</android.support.v4.widget.SwipeRefreshLayout>
```
activity中设置：
```
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
```

###### setOnLoadMoreListener
加载更多
```
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
```

###### setLoadMoreView
设置加载时的页面
```
mAdapter.setLoadMoreView(new CustomLoadMoreView());
```
其中CustomLoadMoreView的代码：
```
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
```
view_load_more的代码：
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="@dimen/dp_40">

    <LinearLayout
        android:id="@+id/load_more_loading_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="horizontal">

        <ProgressBar
            android:id="@+id/loading_progress"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            style="?android:attr/progressBarStyleSmall"
            android:layout_marginRight="@dimen/dp_4"
            android:indeterminateDrawable="@drawable/sample_footer_loading_progress"/>

        <TextView
            android:id="@+id/loading_text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="@dimen/dp_4"
            android:text="@string/loading"
            android:textColor="#0dddb8"
            android:textSize="@dimen/sp_14"/>
    </LinearLayout>

    <FrameLayout
        android:id="@+id/load_more_load_fail_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone">


        <TextView
            android:id="@+id/tv_prompt"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:textColor="#0dddb8"
            android:text="@string/load_failed"/>

    </FrameLayout>

    <FrameLayout
        android:id="@+id/load_more_load_end_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:visibility="gone">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:text="@string/load_end"
            android:textColor="@android:color/darker_gray"/>
    </FrameLayout>
</FrameLayout>
```

加载中有一些状态设置
mAdapter.loadMoreComplete();
mAdapter.loadMoreFail();
mAdapter.loadMoreEnd(true);//不显示没有加载更多（用在第一页没有满）
mAdapter.loadMoreEnd(false);//显示没有加载更多数据

###### setEmptyView
绑定空白页面
```
mAdapter.setEmptyView(R.layout.activity_empty_recycler, (ViewGroup) recyclerView.getParent());
```
需要注意的是第二个参数不能省略

#### ExpandableRecycler使用
这类比较复杂

###### Model
实体类
首先实体类需要两个，先创建子类别的实体类
```
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
```
需要实现MultiItemEntity并且getItemType方法返回和adapter里面的添加页面想对应
父类别的实体类
```
public class ExplandableModel1 extends AbstractExpandableItem<ExplandableModel2> implements MultiItemEntity {
    private int id;
    private String name;

    public ExplandableModel1(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getItemType() {
        return ExpandableRecyclerAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```
这个实体类除了要实现MultiItemEntity还要继承AbstractExpandableItem类型就是子类的类型
同样getItemType也要和adapter添加的页面相对应，并且返回getLevel这个层级

###### adapter
适配器相对比较复杂
```
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
```
需要注意的添加页面和实体类的要对应，还有convert这个方法里面要对应
同时要注意isExpanded这个判断是否展开，同时提供collapse和expand两个方法
并且子类控件的点击事件也在adapter里面

###### activity里面配置
需要注意的是需要添加adapter方法
```
mAdapter.expandAll();
```

数据创建也有些不同，
最外层是个集合，然后子类需要通过addSubItem这个方法来添加
```
List<MultiItemEntity> mData = new ArrayList<>();
for(int i = 0; i < 5; i ++){
    ExplandableModel1 explandableModel1 = new ExplandableModel1(i + 1, "dawn " + i);
    for(int j = 0; j < 2; j ++){
        ExplandableModel2 explandableModel2 = new ExplandableModel2(j + 1, "dawn " + i + " sub dawn " + j);
        explandableModel1.addSubItem(explandableModel2);
    }
    mData.add(explandableModel1);
}
```

## 参考网站
[https://github.com/CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper "参考网站")
