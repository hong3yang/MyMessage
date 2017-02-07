package yang.hong3.com.mymessage.module.hotblog.presenter;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yang.hong3.com.mymessage.bean.hotblog.HotblogBean;
import yang.hong3.com.mymessage.module.hotblog.model.HotblogModel;
import yang.hong3.com.mymessage.module.hotblog.model.HotblogModelImpl;
import yang.hong3.com.mymessage.module.hotblog.view.HotblogResult;

/**
 * Created by hong3 on 2017-1-12.
 */

public class HotblogPresenter {
    private static final String TAG = "HotblogPresenter";
    HotblogResult mHotblogResult;
    HotblogModel mHotblogModel;

    public HotblogPresenter(HotblogResult hotblogResult) {
        mHotblogResult = hotblogResult;
        mHotblogModel = new HotblogModelImpl();
    }

    public void getHotblogData(int num,int page){
        mHotblogModel.getHotblogData(num,page,new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String str = response.body();
                try {
                    JSONObject obj = new JSONObject(str);
                    if (!obj.getBoolean("error")){
                        List<HotblogBean> beanList = JSON.parseArray(obj.getString("results"),HotblogBean.class);
                        mHotblogResult.getDataSuccess(beanList);
                    }else {
                        mHotblogResult.getDataFails("数据解析失败");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mHotblogResult.getDataFails("数据获取失败");
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }
}
