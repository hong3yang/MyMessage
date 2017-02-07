package yang.hong3.com.mymessage.module.live.view;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.adapter.HomeLiveAdapter;
import yang.hong3.com.mymessage.base.BaseFeagmentV4;
import yang.hong3.com.mymessage.bean.live.Banner;
import yang.hong3.com.mymessage.bean.live.LiveBean;
import yang.hong3.com.mymessage.bean.live.Partitions;
import yang.hong3.com.mymessage.bean.live.RecommendData;
import yang.hong3.com.mymessage.customview.CustomEmptyView;
import yang.hong3.com.mymessage.module.live.presenter.HomeLivePresenter;

/**
 * Created by hong3 on 2017-1-9.
 */

public class LiveFragment extends BaseFeagmentV4 implements HomeLiveResult {
    private static final String TAG = "LiveFragment";
    private RecyclerView recyclerView;
    private CustomEmptyView customEmptyView;
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView mTextView;
    GridLayoutManager gridLayoutManager;

    List<Banner> banners = new ArrayList<>();
    //    List<EntranceIcons> entranceIconses = new ArrayList<>();
    List<Partitions> partitionses = new ArrayList<>();
    List<RecommendData> recommendDatas = new ArrayList<>();

    List<LiveBean> mLiveBean = new ArrayList<>();

    HomeLivePresenter mHomeLivePresenter;
    HomeLiveAdapter mHomeLiveAdapter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_main_live;
    }

    @Override
    protected void initView() {
        recyclerView = $(R.id.live_recycleView);
        customEmptyView = $(R.id.live_empty);
        swipeRefreshLayout = $(R.id.swipe);
        mTextView = $(R.id.live_title);
        mTextView.setVisibility(View.GONE);
        mHomeLivePresenter = new HomeLivePresenter(this);


    }

    @Override
    public void initViewComplete() {
        super.initViewComplete();
        isPrepare = true;
        lazyload();
    }

    @Override
    public void lazyload() {
        super.lazyload();
        initRecyclerView();
        initRefreshLayout();
    }

    public void loadData() {
        mHomeLivePresenter.getLiveData();
    }

    private void initRecyclerView() {
//        liveAppIndexAdapter = new LiveAppIndexAdapter();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 12);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return liveAppIndexAdapter.getSpanSize(position);
//            }
//        });
//        recyclerView.setAdapter(liveAppIndexAdapter);
//        recyclerView.setLayoutManager(gridLayoutManager);

        mHomeLiveAdapter = new HomeLiveAdapter(banners, mLiveBean, getActivity());

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mHomeLiveAdapter.getSpanSize(position);
            }
        });
        recyclerView.setAdapter(mHomeLiveAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                             @Override
                                             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                 int position = recyclerView.getChildAdapterPosition(gridLayoutManager.getChildAt(0));
                                                 View nextItem = null;
                                                 if (position == 0) {
                                                     mTextView.setVisibility(View.GONE);
                                                     nextItem = gridLayoutManager.findViewByPosition(1);
                                                 } else {
                                                     mTextView.setVisibility(View.VISIBLE);
                                                     mTextView.setText(mLiveBean.get(position).getArea());
                                                     if (mHomeLiveAdapter.getItemViewType(position + 2) == 2) {
                                                         nextItem = gridLayoutManager.findViewByPosition(position + 2);
                                                     }
                                                 }

                                                 if (nextItem != null && nextItem.getTop() <= mTextView.getHeight()) {
                                                     mTextView.setY(nextItem.getTop() - mTextView.getHeight());
                                                 }else if(position != 0){
                                                     mTextView.setY(0);
                                                     mTextView.setText(mLiveBean.get(position).getArea());
                                                 }
                                             }
                                         }

        );
    }

    public void hideEmptyView() {

        customEmptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void initEmptyView() {
        swipeRefreshLayout.setRefreshing(false);
        customEmptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        customEmptyView.setEmptyImage(R.mipmap.ic_launcher);
        customEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
        Snackbar.make(recyclerView, "数据加载失败,请重新加载或者检查网络是否链接", Snackbar.LENGTH_SHORT).show();
    }

    private void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });
    }

    public static LiveFragment newInstance() {
        return new LiveFragment();
    }

    @Override
    public void getDataSuccess(List<Banner> banners, List<Partitions> partitionses, RecommendData recommendData) {
        this.banners.clear();
        this.banners.addAll(banners);
        swipeRefreshLayout.setRefreshing(false);
        hideEmptyView();

        mLiveBean.clear();
        for (int i = 0, len = partitionses.size(); i < len; i++) {
            LiveBean bean = new LiveBean();
            bean.setArea_id(1234);
            bean.setArea(partitionses.get(i).getLives().get(0).getArea());
            mLiveBean.add(bean);
            mLiveBean.addAll(partitionses.get(i).getLives());
        }

        mHomeLiveAdapter.notifyDataSetChanged();

    }

    @Override
    public void getDataFails(String str) {
        initEmptyView();
        swipeRefreshLayout.setRefreshing(false);
    }
}
