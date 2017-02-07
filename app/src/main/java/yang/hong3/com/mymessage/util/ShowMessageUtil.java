package yang.hong3.com.mymessage.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hong3 on 2017-1-23.
 */

public class ShowMessageUtil {

    static Toast mToast;

    /**
     * 单独工具类，避免因多次调用而反复打印
     * @param context
     * @param str
     */
    public static void toastShow(Context context,String str){
        if (mToast == null){
            mToast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(str);
        }
        mToast.show();
    }
}
