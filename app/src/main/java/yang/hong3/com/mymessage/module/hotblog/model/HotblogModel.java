package yang.hong3.com.mymessage.module.hotblog.model;

import retrofit2.Callback;

/**
 * Created by hong3 on 2017-1-12.
 */

public interface HotblogModel {

    public void getHotblogData(int num,int page,Callback<String> callback);
}
