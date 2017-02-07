package yang.hong3.com.mymessage.module.live.model;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import yang.hong3.com.mymessage.global.Constances;
import yang.hong3.com.mymessage.network.LiveService;
import yang.hong3.com.mymessage.network.RetrofitUtil;

/**
 * Created by hong3 on 2017-1-10.
 */

public class HomeLiveModelImpl implements HomeLiveModel {
    private static final String TAG = "HomeLiveModelImpl";


    @Override
    public void getLiveData(Callback<String> callback) {
        LiveService service = RetrofitUtil.create(LiveService.class, Constances.LIVE_BASE_URL);
        Call<String> call= service.getLiveData();
        Log.d(TAG, "getLiveData: call-->"+call.toString());
        call.enqueue(callback);
    }
}
