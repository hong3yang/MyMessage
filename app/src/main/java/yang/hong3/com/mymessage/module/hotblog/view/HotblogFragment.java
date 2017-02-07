package yang.hong3.com.mymessage.module.hotblog.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.adapter.HotblogAdapter;
import yang.hong3.com.mymessage.base.BaseFeagmentV4;
import yang.hong3.com.mymessage.bean.hotblog.HotblogBean;
import yang.hong3.com.mymessage.customview.EndlessRecyclerOnScrollListener;
import yang.hong3.com.mymessage.module.hotblog.presenter.HotblogPresenter;

/**
 * Created by hong3 on 2017-1-9.
 */

public class HotblogFragment extends BaseFeagmentV4 implements HotblogResult {
    private static final String TAG = "HotblogFragment";
    private RecyclerView mRecyclerView;
    private HotblogAdapter mHotblogAdapter;
    private List<HotblogBean> mList=new ArrayList<>();
    HotblogPresenter mHotblogPresenter;


    public static HotblogFragment newInstance(){
        return new HotblogFragment();
    }

    @Override
    protected void initView() {
        mRecyclerView = $(R.id.hotblog_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mHotblogPresenter = new HotblogPresenter(this);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(manager) {
            @Override
            public void onLoadMore(int currentPage) {
                mHotblogPresenter.getHotblogData(10,currentPage+1);
            }
        });
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
        if (!isPrepare){
            return;
        }

        mHotblogPresenter.getHotblogData(10,1);
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_main_hotblog;
    }

    @Override
    public void getDataSuccess(List<HotblogBean> beanList) {

        mList.clear();
        mList.addAll(beanList);
        Log.d(TAG, "getDataSuccess: "+beanList.size()+"   "+mList.size());
        if (mHotblogAdapter == null){
            mHotblogAdapter = new HotblogAdapter(getActivity(),mList);
            mRecyclerView.setAdapter(mHotblogAdapter);
        }else{
            mHotblogAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getDataFails(String str) {
        fastShow(str);
    }

}
