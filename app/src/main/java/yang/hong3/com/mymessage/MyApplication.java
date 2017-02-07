package yang.hong3.com.mymessage;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by hong3 on 2017-1-10.
 */

public class MyApplication extends Application{

    public static short myPreset = -1;


    @Override
    public void onCreate() {
        super.onCreate();

        QbSdk.initX5Environment(this,null);
    }
}
