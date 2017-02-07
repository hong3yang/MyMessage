package yang.hong3.com.mymessage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import yang.hong3.com.mymessage.module.hotblog.view.HotblogFragment;
import yang.hong3.com.mymessage.module.live.view.LiveFragment;
import yang.hong3.com.mymessage.module.music.view.MusicFragment;
import yang.hong3.com.mymessage.module.near.view.NearFragment;

/**
 * Created by hong3 on 2017-1-9.
 */

public class MainViewpagerAdapter extends FragmentPagerAdapter {

    String[] mStrings;
   Fragment[] mFragments;

    public MainViewpagerAdapter(FragmentManager fm, String[] strings) {
        super(fm);
        mStrings = strings;
        mFragments = new Fragment[mStrings.length];

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                //热门博客
                fragment = HotblogFragment.newInstance();
                break;
            case 1:
                //直播
                fragment = LiveFragment.newInstance();
                break;
//            case 2:
//                //图片
//                fragment = ImageFragment.newInstance();
//                break;
            case 2:
                //音乐
                fragment = MusicFragment.newInstance();
                break;
            case 3:
                //附近
                fragment = NearFragment.newInstance();
                break;
        }


        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings[position];
    }
}
