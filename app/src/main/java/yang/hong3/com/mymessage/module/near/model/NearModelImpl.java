package yang.hong3.com.mymessage.module.near.model;

import retrofit2.Call;
import retrofit2.Callback;
import yang.hong3.com.mymessage.network.NearService;
import yang.hong3.com.mymessage.network.RetrofitUtil;

/**
 * Created by hong3 on 2017-1-22.
 */

public class NearModelImpl implements NearModel{

    private static final String NEAR_BASE_URL = "http://apis.juhe.cn/";
    private static final String NEAR_KEY = "68a5e7082a0831e5c8337862919be3c2";
    Call<String> call;
    @Override
    public void getNearList(String longitude, String latitude, String radius, String page, Callback<String> callback) {
        if(call != null && call.isExecuted()){
            call.cancel();
        }
        NearService service = RetrofitUtil.create(NearService.class,NEAR_BASE_URL);
        call = service.getNearList(longitude,latitude,  radius,  page,  NEAR_KEY);
        call.enqueue(callback);
    }
}
