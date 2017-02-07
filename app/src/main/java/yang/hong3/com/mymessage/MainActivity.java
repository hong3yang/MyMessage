package yang.hong3.com.mymessage;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import yang.hong3.com.mymessage.adapter.MainViewpagerAdapter;
import yang.hong3.com.mymessage.base.BaseActivity;


public class MainActivity extends BaseActivity {

    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    String[] strs = {"热门博客","直播","音乐","附近"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mToolbar = $(R.id.main_toolbar);
        mTabLayout = $(R.id.main_tablayout);
        mViewPager = $(R.id.main_viewpager);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void initData() {
        super.initData();


        mViewPager.setAdapter(new MainViewpagerAdapter(getSupportFragmentManager(),strs));
        mTabLayout.setupWithViewPager(mViewPager);
    }



    public static void startActivity(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }


}
