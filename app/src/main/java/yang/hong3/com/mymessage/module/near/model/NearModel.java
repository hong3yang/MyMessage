package yang.hong3.com.mymessage.module.near.model;

import retrofit2.Callback;

/**
 * Created by hong3 on 2017-1-22.
 */

public interface NearModel {

    public void getNearList(String longitude, String latitude, String radius, String page, Callback<String> callback);
}
