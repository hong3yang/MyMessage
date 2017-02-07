package yang.hong3.com.mymessage.module.music.view;

import java.util.List;

import yang.hong3.com.mymessage.bean.music.MusicListBean;

/**
 * Created by hong3 on 2017-1-13.
 */

public interface MusicResult {

    public void getMusicListSuccess(List<MusicListBean> list);
    public void getMusicListFails(String str);
}
