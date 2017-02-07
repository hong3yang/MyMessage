package yang.hong3.com.mymessage.module.music.model;

import retrofit2.Callback;

/**
 * Created by hong3 on 2017-1-13.
 */

public interface MusicModel {

    public void getMusicList(String keywords,int pageSize,int page,Callback<String> callback);
    public void getMUsicPlayInfo(Callback<String> callback);
    public void getMusicSinger(Callback<String> callback);
    public void getMusicLrc(Callback<String> callback);
}
