package yang.hong3.com.mymessage.module.music.model;

import retrofit2.Callback;

/**
 * Created by hong3 on 2017-1-13.
 */

public class MusicModelImpl implements MusicModel {

    private final static String BASE_MUSIC_URL = "http://mobilecdn.kugou.com/";

    @Override
    public void getMusicList(String keywords,int pageSize,int page,Callback<String> callback) {
//        MusicService service= RetrofitUtil.createMusic(MusicService.class,BASE_MUSIC_URL);
//        Call<String> call = service.getMusicList(keywords,pageSize+"",""+page);
//
//        call.enqueue(callback);
    }

    @Override
    public void getMUsicPlayInfo(Callback<String> callback) {

    }

    @Override
    public void getMusicSinger(Callback<String> callback) {

    }

    @Override
    public void getMusicLrc(Callback<String> callback) {

    }


}
