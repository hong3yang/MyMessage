package yang.hong3.com.mymessage.module.live.presenter;


import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yang.hong3.com.mymessage.bean.live.Banner;
import yang.hong3.com.mymessage.bean.live.Partitions;
import yang.hong3.com.mymessage.bean.live.RecommendData;
import yang.hong3.com.mymessage.module.live.model.HomeLiveModel;
import yang.hong3.com.mymessage.module.live.model.HomeLiveModelImpl;
import yang.hong3.com.mymessage.module.live.view.HomeLiveResult;

/**
 * Created by hong3 on 2016/12/3.
 */

public class HomeLivePresenter {

    private static final String TAG = "HomeLivePresenter";
    HomeLiveModel mHomeLiveModel;
    HomeLiveResult mHomeLiveResult;


    public HomeLivePresenter(HomeLiveResult homeLiveResult) {
        mHomeLiveResult = homeLiveResult;
        mHomeLiveModel = new HomeLiveModelImpl();
    }

    public void getLiveData() {

        mHomeLiveModel.getLiveData(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String str = response.body();

                try {
                    JSONObject obj = new JSONObject(str);
                    if (obj.getInt("code") == 0){
                        JSONObject dataObj = obj.getJSONObject("data");
                        List<Banner> banners = JSON.parseArray(dataObj.getString("banner"), Banner.class);
//                        List<EntranceIcons> entranceIconses = JSON.parseArray(dataObj.getString("entranceIcons"), EntranceIcons.class);
                        List<Partitions> partitionses = JSON.parseArray(dataObj.getString("partitions"),Partitions.class);
                        RecommendData recommendData = JSON.parseObject(dataObj.getString("recommend_data"),RecommendData.class);
                        mHomeLiveResult.getDataSuccess(banners,partitionses,recommendData);

                    }else{
                        mHomeLiveResult.getDataFails(obj.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    mHomeLiveResult.getDataFails("解析失败"+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                mHomeLiveResult.getDataFails("数据获取失败"+throwable.getMessage());
            }
        });
    }
}
