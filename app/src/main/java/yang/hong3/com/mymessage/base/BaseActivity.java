package yang.hong3.com.mymessage.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by hong3 on 2017-1-9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        getInstanceState(savedInstanceState);
        initView();
        initData();
    }

    public void getInstanceState(Bundle savedInstanceState) {

    }

    public void initData() {
    }

    public abstract int getLayoutId();

    protected abstract void initView();

    public <T extends View> T $(int id){
        return (T) findViewById(id);
    }

    public void fastShow(View v,String str){
        Snackbar.make(v,str,Snackbar.LENGTH_SHORT).show();
    }
}
