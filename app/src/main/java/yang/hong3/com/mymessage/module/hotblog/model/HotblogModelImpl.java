package yang.hong3.com.mymessage.module.hotblog.model;

import retrofit2.Call;
import retrofit2.Callback;
import yang.hong3.com.mymessage.network.HotblogService;
import yang.hong3.com.mymessage.network.RetrofitUtil;

/**
 * Created by hong3 on 2017-1-12.
 */

public class HotblogModelImpl implements HotblogModel{

    private final static String HOTBLOG_URL = "http://gank.io/api/data/";

    @Override
    public void getHotblogData(int num,int page,Callback<String> callback) {
        HotblogService service = RetrofitUtil.create(HotblogService.class, HOTBLOG_URL);
        Call<String> call = service.getHotblogData(num+"",page+"");
        call.enqueue(callback);
    }
}
