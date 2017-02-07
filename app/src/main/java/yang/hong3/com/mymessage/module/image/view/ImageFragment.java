package yang.hong3.com.mymessage.module.image.view;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.base.BaseFeagmentV4;

/**
 * Created by hong3 on 2017-1-9.
 */

public class ImageFragment extends BaseFeagmentV4 {

    public static ImageFragment newInstance(){
        return new ImageFragment();
    }
    @Override
    protected void initView() {

    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_main_image;
    }
}
