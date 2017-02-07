package yang.hong3.com.mymessage.module.music.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yang.hong3.com.mymessage.bean.music.MusicListBean;
import yang.hong3.com.mymessage.module.music.model.MusicModel;
import yang.hong3.com.mymessage.module.music.model.MusicModelImpl;
import yang.hong3.com.mymessage.module.music.view.MusicResult;

/**
 * Created by hong3 on 2017-1-13.
 */

public class MusicPresenter {
    private static final String TAG = "MusicPresenter";
    MusicResult mMusicResult;
    MusicModel mMusicModel;

    public MusicPresenter(MusicResult musicResult) {
        mMusicResult = musicResult;
        mMusicModel = new MusicModelImpl();
    }

    public void getMusicList(String keywords,int pageSize,int page){
        mMusicModel.getMusicList(keywords,pageSize,page,new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    JSONObject obj = new JSONObject(response.body());
                    if(obj.getInt("status") == 1){
                        String str = obj.getJSONObject("data").getString("info");
                        Log.d(TAG, "onResponse: "+str);
                        str = str.replace("320hash","hash320").replace("320filesize","filesize320").replace("320privilege","privilege320");
                        List<MusicListBean> list = JSON.parseArray(str,MusicListBean.class);
                        mMusicResult.getMusicListSuccess(list);

                    }else{
                        mMusicResult.getMusicListFails(obj.getString("error"));


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


    public void getLocalMusicList(Context context){
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor =resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null,null);
        List<MusicListBean> beanList = new ArrayList<>();
        while(!cursor.isAfterLast() && cursor.moveToNext()){
            MusicListBean bean = new MusicListBean();
            bean.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
            bean.setFilename(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
            bean.setFilesize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE)));
            bean.setSingername(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
            bean.setHash(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
            beanList.add(bean);
        }

        mMusicResult.getMusicListSuccess(beanList);

    }
}
