package yang.hong3.com.mymessage.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hong3 on 2017-1-10.
 */

public interface LiveService {

    @GET("AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758")
    public Call<String> getLiveData();


}
