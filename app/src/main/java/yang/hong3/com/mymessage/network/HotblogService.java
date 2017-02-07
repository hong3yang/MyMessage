package yang.hong3.com.mymessage.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by hong3 on 2017-1-12.
 */

public interface HotblogService {

    @GET("Android/{num}/{page}")
    public Call<String> getHotblogData(@Path("num")String num,@Path("page")String page);
}
