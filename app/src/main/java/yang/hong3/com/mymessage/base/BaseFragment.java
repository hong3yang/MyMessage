package yang.hong3.com.mymessage.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hong3 on 2017-1-9.
 */

public abstract class BaseFragment extends Fragment{


    private int resourceId;
    private View rootView;
    private boolean isVisible;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        resourceId = getResourceId();
        rootView = inflater.inflate(resourceId,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewComplete();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()){
            isVisible = true;
            loadData();
        }else{
            isVisible = false;
            onInvisible();
        }
    }

    public void fastShow(String str){
        Snackbar.make(rootView,str,Snackbar.LENGTH_SHORT).show();
    }

    public <T extends View>T $(int id){
        return (T) rootView.findViewById(id);
    }

    public void onInvisible() {

    }

    public void loadData() {

    }

    public void initViewComplete() {

    }

    protected abstract void initView();

    public abstract int getResourceId();
}
