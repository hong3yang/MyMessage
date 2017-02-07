package yang.hong3.com.mymessage.module.near.presenter;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yang.hong3.com.mymessage.bean.near.NearBean;
import yang.hong3.com.mymessage.module.near.model.NearModel;
import yang.hong3.com.mymessage.module.near.model.NearModelImpl;
import yang.hong3.com.mymessage.module.near.view.NearFragmentResult;

/**
 * Created by hong3 on 2017-1-22.
 */

public class NearPresenter {

    NearFragmentResult mNearResult;
    NearModel mNearModel;

    public NearPresenter(NearFragmentResult nearResult) {
        mNearResult = nearResult;
        mNearModel = new NearModelImpl();
    }

    public void getNearList(double longitude, double latitude, int radius, int page){
        mNearModel.getNearList(longitude + "", latitude + "", radius + "", page + "", new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int currentPage = 0;
                int totalPage = 0;
                try {
                    JSONObject obj = new JSONObject(response.body());
                    if (obj.getInt("resultcode")==200){
                        currentPage = obj.getJSONObject("result").getJSONObject("pageinfo").getInt("current");
                        totalPage = obj.getJSONObject("result").getJSONObject("pageinfo").getInt("allpage");
                        String  str= obj.getJSONObject("result").getString("data");
                        List<NearBean> nearBeanList = JSON.parseArray(str,NearBean.class);
                        mNearResult.getNearListSuccess(currentPage,totalPage,nearBeanList);

                    }else{
                        mNearResult.getNearListFails(obj.getString("reason"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

}
