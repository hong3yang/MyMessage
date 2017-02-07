package yang.hong3.com.mymessage.module.guide.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.MainActivity;
import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.base.BaseActivity;
import yang.hong3.com.mymessage.util.DensityUtil;

/**
 * Created by hong3 on 2017-1-9.
 */

public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final String TAG = "GuideActivity";
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;

    Button button;
    ImageView redPoint;
    int[] images = new int[]{R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};

    List<ImageView> imageViews = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        mViewPager = $(R.id.guide_pager);
        mLinearLayout = $(R.id.guide_indicator);
        redPoint = (ImageView) findViewById(R.id.guide_redpoint);
        redPoint.setImageResource(R.drawable.welcom_indicator_choose);
        button = (Button) findViewById(R.id.guide_start);
        button.setOnClickListener(this);

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.welcom_indicator);
            int radius = DensityUtil.dip2px(this, 10.0f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(radius, radius);
            if (i != 0) {
                params.setMargins(radius, 0, 0, 0);
            }
            imageView.setLayoutParams(params);
            mLinearLayout.addView(imageView);
        }
    }

    ViewpagerAdapter mViewpagerAdapter;

    @Override
    public void initData() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[i]);
            imageViews.add(imageView);
        }
        mViewpagerAdapter = new ViewpagerAdapter();
        mViewPager.setAdapter(mViewpagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        //进入主页面
        MainActivity.startActivity(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int maxWidth = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
        int distance = (int) (maxWidth * (position + positionOffset));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10));
        params.setMargins(distance, 0, 0, 0);
        redPoint.setLayoutParams(params);

        if (positionOffset != 0) {
            imageViews.get(position).setScaleX(1 - positionOffset);
            imageViews.get(position + 1).setScaleX(positionOffset);
            int width = imageViews.get(position).getWidth();
            int x =  width*(position+1);
            imageViews.get(position + 1).setX(((width * (positionOffset - 1) / 2)+x));

        } else {
            Log.d(TAG, "onPageScrolled: "+imageViews.get(position).getX());
        }


    }

    @Override
    public void onPageSelected(int position) {
        if (position == imageViews.size() - 1) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    class ViewpagerAdapter extends PagerAdapter {

        /**
         * pager页数
         *
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 创建pager
         *
         * @param container 容器，就是viewpager
         * @param position  要创建页面的位置
         * @return 返回 与当前创建页面的关系值（可以为当前页面的对象或布局，也可以为当前页面的位置-->也就是position）
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        /**
         * 判断是否为当前页
         *
         * @param view   页面布局
         * @param object 上面方法返回的结果（如果上面返回为布局，可以直接相等；若为position位置，需要通过传入的集合得到布局）
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        /**
         * 销毁页面
         *
         * @param container viewpager
         * @param position  销毁页面位置
         * @param object    销毁页面的布局
         */

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);   此方法必须注释掉
            container.removeView((View) object);
        }
    }


}
