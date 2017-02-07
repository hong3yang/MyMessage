package yang.hong3.com.mymessage.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hong3 on 2017-1-13.
 */

public interface MusicService {

//    @GET("/krc")
//    public Call<String> getMusicLrc(@Query("name")String fileName,@Query("hash")String hash,@Query("time")String duration);
//
//    @GET("/singer")
//    public Call<String> getMusicSinger(@Query("name")String singerName);
//
//    @GET("/playinfo")
//    public Call<String> getMusicPlayInfo(@Query("hash")String hash);

    @GET("/api/v3/search/song")  //?keyword=%E4%BB%99%E5%89%91&page=1&pagesize=10
    public Call<String> getMusicList(@Query("keyword")String searchKey,@Query("pagesize")String size,@Query("page")String page);

    @GET("/i")
    public Call<String> getMusicPlayinfo(@Query("hash")String hash, @Query("key")String key, @Query("cmd")String cmd, @Query("pid")String pid, @Query("acceptMp3")String acceptMp3);

}
