package yang.hong3.com.mymessage.customview.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by hong3 on 2016/12/3.
 */

public class BannerAdapter extends PagerAdapter {

    private List<ImageView> imageViewList;

    private ViewPagerOnItemClickListener viewPagerOnItemClickListener;

    public void setViewPagerOnItemClickListener(ViewPagerOnItemClickListener viewPagerOnItemClickListener){
        this.viewPagerOnItemClickListener = viewPagerOnItemClickListener;
    }

    public BannerAdapter(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }

    public interface ViewPagerOnItemClickListener{
        void onClickLinstener();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
        object = null;

    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (position < 0){
            position = imageViewList.size()-1;
        }
        ImageView image = imageViewList.get(position);
        image.setScaleType(ImageView.ScaleType.CENTER);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = image.getParent();
        if (vp != null){
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(image);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPagerOnItemClickListener != null){
                    viewPagerOnItemClickListener.onClickLinstener();
                }
            }
        });

        container.addView(image);

        return image;
    }
}
