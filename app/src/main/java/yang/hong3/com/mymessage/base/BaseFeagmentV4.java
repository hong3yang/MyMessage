package yang.hong3.com.mymessage.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * 定义懒加载过程
 * Created by hong3 on 2017-1-9.
 */

public abstract class BaseFeagmentV4 extends Fragment {
    private static final String TAG = "BaseFeagmentV4";
    private int resourceId;
    private boolean isVisible;
    View rootView;
    public boolean isPrepare;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        resourceId = getResourceId();
        rootView = inflater.inflate(resourceId,container,false);
        return rootView;
    }

    /**
     * 初始化view
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewComplete();

    }

    /**
     * 显示当前页，实现数据懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //是否
        if (getUserVisibleHint()){
            //可见--开始加载数据
            isVisible = true;
            lazyload();
        }else{
            //不可见--完成页面隐藏的操作
            isVisible = false;
            onInvisible();
        }
    }


    public <T extends View> T $(int id){
        return (T) rootView.findViewById(id);
    }

    public void fastShow(String str){
        Snackbar.make(rootView,str,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 页面隐藏需要做的逻辑（保存页面数据等）
     */
    public void onInvisible() {
    }

    /**
     * 加载用户数据
     */
    public void lazyload() {

    }

    /**
     * 初始化控件完成
     */
    public void initViewComplete() {
    }

    /**
     * 获取布局id
     * @return
     */
    public abstract int getResourceId();

    /**
     * 初始化控件
     */
    protected abstract void initView();
}
