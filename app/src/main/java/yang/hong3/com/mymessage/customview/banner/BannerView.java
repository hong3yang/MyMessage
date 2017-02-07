package yang.hong3.com.mymessage.customview.banner;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.bean.live.Banner;
import yang.hong3.com.mymessage.util.DensityUtil;



/**
 * Created by hong3 on 2016/12/3.
 */

public class BannerView extends LinearLayout {

    private static final String TAG = "BannerView";

    ViewPager viewPager;
    LinearLayout points;

    //默认轮播时间，10s
    private int delayTime = 10;

    private List<ImageView> imageViewList;

    private List<Banner> bannerList;

    //选中显示Indicator
    private int selectRes = R.drawable.shape_dots_select;

    //非选中显示Indicator
    private int unSelcetRes = R.drawable.shape_dots_default;

    private boolean isStopScroll = false;

    //当前页的下标
    private int currrentPos=0;


    public BannerView(Context context)
    {

        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs)
    {

        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr)
    {

        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_banner, this, true);

        initView();
    }


    private Handler handler;

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.layout_banner_viewpager);
        points = (LinearLayout) findViewById(R.id.layout_banner_points_group);

    }

    public void build(final List<Banner> banners)
    {
        if (banners.size() == 0){
            this.setVisibility(GONE);
            return;
        }

        this.setVisibility(VISIBLE);
        bannerList = new ArrayList<>();
        bannerList.addAll(banners);
        final int pointSize;
        pointSize = bannerList.size();


        // FIXME: 2016/12/3 未知功能的东西
//        if (pointSize == 2){
//            bannerList.addAll(banners);
//        }

        //判断是否清空 指示器点
        if (points.getChildCount() != 0)
        {
            points.removeAllViewsInLayout();
        }

        //初始化指示器
        for (int i = 0; i < pointSize; i++) {
            View dot = new View(getContext());
            dot.setBackgroundResource(unSelcetRes);
            LayoutParams params = new LayoutParams(DensityUtil.dip2px(getContext(),5),DensityUtil.dip2px(getContext(),5));
            params.leftMargin = 10;
            dot.setLayoutParams(params);
            points.addView(dot);

        }
        //设置默认选中项
        points.getChildAt(0).setBackgroundResource(selectRes);

        //初始化viewpager
        imageViewList = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView imageView = new ImageView(getContext());

            int screenWidth = ((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth();   //获取屏幕宽
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(bannerList.get(i).getImg())
                    .resize(screenWidth,DensityUtil.dip2px(getContext(),126))  //图片填充，需要填入确定值
                    .into(imageView);

            imageViewList.add(imageView);
        }
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < points.getChildCount(); i++) {
                    points.getChildAt(i).setBackgroundResource(unSelcetRes);
                }
                points.getChildAt(position).setBackgroundResource(selectRes);
                currrentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state)
                {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll){
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopScroll();
                        break;

                }
            }
        });

        BannerAdapter bannerAdapter = new BannerAdapter(imageViewList);
        viewPager.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        bannerAdapter.setViewPagerOnItemClickListener(new BannerAdapter.ViewPagerOnItemClickListener() {
            @Override
            public void onClickLinstener() {
//                Toast.makeText(getContext(),"你点击了"+bannerList.get(currrentPos%bannerList.size()).getTitle(),Toast.LENGTH_SHORT).show();
                WebViewActivity.startActivity(getContext(),bannerList.get(currrentPos%bannerList.size()).getLink());
            }
        });

        startScroll();
    }


    /**
     * 图片开始轮播
     */
    private void startScroll()
    {
        if (handler != null){
            handler.sendEmptyMessageDelayed(1,delayTime*1000);
        }else{
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == 1){
                        if (!isStopScroll) {
                            currrentPos++;
                            viewPager.setCurrentItem(currrentPos % bannerList.size());
                            handler.sendEmptyMessageDelayed(1, delayTime * 1000);
                        }
                    }
                }
            };
            handler.sendEmptyMessageDelayed(1,delayTime*1000);
        }

        isStopScroll = false;

    }

    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    public BannerView delayTime(int time)
    {

        this.delayTime = time;
        return this;
    }

    /**
     * 设置Points资源 Res
     *
     * @param selectRes   选中状态
     * @param unselcetRes 非选中状态
     */
    public void setPointsRes(int selectRes, int unselcetRes)
    {

        this.selectRes = selectRes;
        this.unSelcetRes = unselcetRes;
    }

    /**
     * 图片停止轮播
     */
    private void stopScroll()
    {
        isStopScroll = true;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScroll();
        if (handler != null){
            handler.removeMessages(1);
           
        }
        handler = null;
    }




}
