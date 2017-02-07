package yang.hong3.com.mymessage.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hong3 on 2017-1-22.
 */

public interface NearService {

    @GET("/oil/local") //lon=121&lat=31&r=4000&page=&format=&key=68a5e7082a0831e5c8337862919be3c2
    public Call<String> getNearList(@Query("lon")String longitude,@Query("lat")String latitude,@Query("r")String radius,@Query("page")String page,@Query("key")String key);
}
