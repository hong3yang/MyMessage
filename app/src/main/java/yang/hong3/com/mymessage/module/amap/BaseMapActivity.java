package yang.hong3.com.mymessage.module.amap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.bean.near.NearBean;
import yang.hong3.com.mymessage.module.near.presenter.NearPresenter;
import yang.hong3.com.mymessage.module.near.view.NearFragmentResult;
import yang.hong3.com.mymessage.util.ShowMessageUtil;

public class BaseMapActivity extends AppCompatActivity implements AMapLocationListener, LocationSource, NearFragmentResult, AMap.InfoWindowAdapter, AMap.OnInfoWindowClickListener, RouteSearch.OnRouteSearchListener {
    private static final String TAG = "BaseMapActivity";
    private final static String LAT = "latitude";
    private final static String LOG = "longitude";


    MapView mMapView;
    AMap mAMap;
    UiSettings uiSettings;

    double latitude = -1, longitude = -1;
    LatLng mLatLng, myLatLng,endLatlng;

    AMapLocationClient mLocationClient;
    AMapLocationClientOption mClientOption;
    OnLocationChangedListener mChangedListener;

    NearPresenter mNearPresenter;
    List<NearBean> mBeanList = new ArrayList<>();
    RouteSearch.FromAndTo mFromAndTo;
    RouteSearch.DriveRouteQuery mDriveRouteQuery;
    private RouteSearch mRouteSearch;
    private DrivingRouteOverlay mDrivingRouteOverlay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_map);

        Intent intent = getIntent();
        if (intent != null) {
            latitude = intent.getDoubleExtra(LAT, -1);
            longitude = intent.getDoubleExtra(LOG, -1);
            mLatLng = new LatLng(latitude, longitude);
        }

        if (mMapView == null) {
            mMapView = $(R.id.base_mapview);
            mMapView.onCreate(savedInstanceState);
        }

        //初始化定位操作
        initLocation();
        //初始化地图
        initMap();
        //设置默认居中的点
        setCenterPoint();
        //添加默认的maker
        addMarker(mLatLng, "sss");
        //初始化定位点样式
        initLocationMarkerStyle();

        mNearPresenter = new NearPresenter(this);

        click();


    }

    private void initNavi(LatLng start, LatLng end) {

        if (end != null) {

            if (mFromAndTo != null) {
                mFromAndTo = null;
            }
            if (mDriveRouteQuery != null) {
                mDriveRouteQuery = null;
            }
            Log.d(TAG, "initNavi: "+start+"   "+end);
            LatLonPoint startPoint = new LatLonPoint(start.latitude, start.longitude);
            LatLonPoint endPoint = new LatLonPoint(end.latitude, end.longitude);

            mFromAndTo = new RouteSearch.FromAndTo(startPoint, endPoint);
            mDriveRouteQuery = new RouteSearch.DriveRouteQuery(mFromAndTo, RouteSearch.DrivingShortDistance, null, null, null);
            // 异步路径规划驾车模式查询
            mRouteSearch.calculateDriveRouteAsyn(mDriveRouteQuery);


        }


//        mAMapNavi = AMapNavi.getInstance(this);
//        mAMapNavi.addAMapNaviListener(this);
    }

    private void click() {
        mAMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mNearPresenter.getNearList(latLng.longitude, latLng.latitude, 10000, 1);
            }
        });

        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //开始路径规划
                if (mDrivingRouteOverlay != null){
                    mDrivingRouteOverlay.removeFromMap();
                }
                endLatlng = marker.getPosition();
                initNavi(myLatLng, endLatlng);
                //显示信息窗口
                marker.showInfoWindow();
                ShowMessageUtil.toastShow(BaseMapActivity.this, getItemByName(marker.getTitle()).getName());
                return true;
            }
        });
    }

    private void initLocationMarkerStyle() {
        MyLocationStyle style = new MyLocationStyle();
        style.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location_map_gps_locked));
        style.radiusFillColor(Color.parseColor("#33ddfa05"));
        style.strokeColor(Color.GREEN);
        style.strokeWidth(2);
        mAMap.setMyLocationStyle(style);
    }

    private void initLocation() {

        mLocationClient = new AMapLocationClient(this);
        mClientOption = new AMapLocationClientOption();
        mClientOption.setOnceLocationLatest(true);
        mClientOption.setHttpTimeOut(1200);
        mClientOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mClientOption);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }


    private void addMarker(double lat, double lng, String title) {
        addMarker(new LatLng(lat, lng), title);
    }

    private void addMarker(LatLng latLng, String title) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).title(title).anchor(1.2f, 1.5f);
        mAMap.addMarker(options);
    }

    private void setCenterPoint() {
        mAMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(14));
    }

    private void initMap() {
        mAMap = mMapView.getMap();

        //设置显示的界面元素（指南针，刻度尺等）
        uiSettings = mAMap.getUiSettings();
        uiSettings.setCompassEnabled(true);//指南针
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);

        mAMap.setMyLocationEnabled(true);

        //启动自动定位
        mAMap.setLocationSource(this);

        //设置marker的信息窗口
        mAMap.setInfoWindowAdapter(this);
        mAMap.setOnInfoWindowClickListener(this);

        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

    }


    public static void startActivity(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, BaseMapActivity.class);
        intent.putExtra(LAT, latitude);
        intent.putExtra(LOG, longitude);
        context.startActivity(intent);
    }

    public <T extends View> T $(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mChangedListener != null && aMapLocation != null && aMapLocation.getErrorCode() == 0) {
            mAMap.clear();
            myLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            mChangedListener.onLocationChanged(aMapLocation);
            mNearPresenter.getNearList(aMapLocation.getLongitude(), aMapLocation.getLatitude(), 10000, 1);
        } else {
            //显示定位图标
            myLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            addMyLocationMarker(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
        }
    }

    private void addMyLocationMarker(LatLng latLng) {
        MarkerOptions options = new MarkerOptions();
        options.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_map_gps_locked));
        mAMap.addMarker(options);
    }

    private NearBean getItemByName(String name) {
        for (NearBean bean : mBeanList) {
            if (name.equals(bean.getName())) {
                return bean;
            }
        }
        return null;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.d(TAG, "activate: " + (mLocationClient == null));
        mChangedListener = onLocationChangedListener;
        initLocation();
    }

    @Override
    public void deactivate() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }

    @Override
    public void getNearListSuccess(int currentPage, int totalPage, List<NearBean> nearBeanList) {
        mBeanList.clear();
        mAMap.clear();
        mBeanList.addAll(nearBeanList);
        for (NearBean bean : nearBeanList) {
            addMarker(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLon()), bean.getName());
        }
    }

    @Override
    public void getNearListFails(String str) {
        ShowMessageUtil.toastShow(this, str);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = initInfoView(marker);

        return v;
    }

    @NonNull
    private View initInfoView(Marker marker) {
        View v = this.getLayoutInflater().inflate(R.layout.layout_amap_infowindow, null);
        TextView name = (TextView) v.findViewById(R.id.map_infowindow_name);
        TextView address = (TextView) v.findViewById(R.id.map_infowindow_address);
        Button button = (Button) v.findViewById(R.id.map_infowindow_navi_btn);

        NearBean bean = getItemByName(marker.getTitle());
        if (bean != null) {
            name.setText(bean.getName());
            address.setText(bean.getAddress());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowMessageUtil.toastShow(BaseMapActivity.this, "开启导航");
                }
            });
        } else {
            name.setText("");
            address.setText("");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowMessageUtil.toastShow(BaseMapActivity.this, "数据异常，请刷新数据或检查网络后重试");
                }
            });
        }
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = initInfoView(marker);

        return v;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if (i == 1000 && driveRouteResult != null && driveRouteResult.getPaths() != null && driveRouteResult.getPaths().size() > 0) {
            List<DrivePath> paths = driveRouteResult.getPaths();


            mDrivingRouteOverlay = new DrivingRouteOverlay(this, mAMap, paths.get(0),
                    driveRouteResult.getStartPos(),
                    driveRouteResult.getTargetPos(),
                    null);

            mDrivingRouteOverlay.setNodeIconVisibility(false);
            mDrivingRouteOverlay.removeFromMap();
            mDrivingRouteOverlay.addToMap();


            if (myLatLng != null && endLatlng != null){
                LatLngBounds.Builder bounds = new LatLngBounds.Builder();
                bounds.include(myLatLng);
                bounds.include(endLatlng);

                mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(),200),400,null);
            }

//            float distance = 0.0f;
//            List<DriveStep> lists= paths.get(0).getSteps();
//            for (int j = 0; j < lists.size(); j++) {
//                distance += lists.get(j).getDistance();
//            }



        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

//    /**
//     * 路径规划初始化成功。开始计算路径
//     */
//    @Override
//    public void onInitNaviSuccess() {
//        int strategy=0;
//        try {
//            /**
//             * 参数分别代表：躲避拥堵，不走高速，避免收费，高速优先，多路径（true会算出多条路线）
//             * 注意：相互冲突的项不能同时为true（不走高速与高速优先，避免收费与高速优先）
//             */
//            strategy = mAMapNavi.strategyConvert(true,false,false,false,false);//配置算路策略
//            mAMapNavi.calculateDriveRoute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onCalculateRouteSuccess() {
//
//    }
//
//    /**
//     * 路径规划失败
//     */
//    @Override
//    public void onInitNaviFailure() {
//
//    }


}
