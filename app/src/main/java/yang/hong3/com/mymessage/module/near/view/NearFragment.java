package yang.hong3.com.mymessage.module.near.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.adapter.NearAdapter;
import yang.hong3.com.mymessage.base.BaseFeagmentV4;
import yang.hong3.com.mymessage.bean.near.NearBean;
import yang.hong3.com.mymessage.module.near.presenter.NearPresenter;

import static com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;

/**
 * Created by hong3 on 2017-1-9.
 */

public class NearFragment extends BaseFeagmentV4 implements NearFragmentResult {
    private static final String TAG = "NearFragment";
    RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    NearAdapter adapter;
    List<NearBean> mNearBeen = new ArrayList<>();
    NearPresenter mNearPresenter;
    int raduis = 5000;
    int currentPage = 1;
    int totalPage = 1;


    /*======================*/
    AMapLocationClient mLocationClient;
    AMapLocationClientOption mClientOption;
    double latitude,longitude;

    @Override
    protected void initView() {

        mRecyclerView = $(R.id.near_recycle);
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        adapter = new NearAdapter(getActivity(),mNearBeen);
        mRecyclerView.setAdapter(adapter);
        mNearPresenter = new NearPresenter(this);

        mLocationClient = new AMapLocationClient(getActivity());
        mClientOption = new AMapLocationClientOption();
        mClientOption.setOnceLocationLatest(true);
        mClientOption.setLocationMode(Hight_Accuracy);
        mLocationClient.setLocationOption(mClientOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null){
                    if (aMapLocation.getErrorCode() == 0) {
                        latitude = aMapLocation.getLatitude();
                        longitude = aMapLocation.getLongitude();
                        Log.d(TAG, "onLocationChanged: latitude:" + latitude + "  longitude:" + longitude);
                        mNearPresenter.getNearList(longitude, latitude, raduis, currentPage);
                    }else{
                        Toast.makeText(getActivity(),"Error: "+aMapLocation.getErrorInfo(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        Log.d(TAG, "initView: ");
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
        mLocationClient.startLocation();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_main_video;
    }

    public static NearFragment newInstance() {
        return new NearFragment();
    }

    @Override
    public void getNearListSuccess(int currentPage, int totalPage, List<NearBean> nearBeanList) {
        mNearBeen.clear();
        mNearBeen.addAll(nearBeanList);
        Log.d(TAG, "getNearListSuccess: "+nearBeanList.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getNearListFails(String str) {

    }
}
