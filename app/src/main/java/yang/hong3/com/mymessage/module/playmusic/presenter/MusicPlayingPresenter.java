package yang.hong3.com.mymessage.module.playmusic.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import yang.hong3.com.mymessage.bean.music.MusicListBean;

/**
 * Created by hong3 on 2017-1-18.
 */

public class MusicPlayingPresenter {

    /**
     * 根据路径获取本地音乐信息详情
     * @param path
     */
    public static MusicListBean getMusicInfo(Context context,String path){
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Audio.AudioColumns.DATA+"=?",new String[]{path},null);
        MusicListBean bean = null;
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            bean = new MusicListBean();
            bean.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
            bean.setFilename(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
            bean.setFilesize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE)));
            bean.setSingername(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
            bean.setHash(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
        }
        return bean;
    }
}
