package yang.hong3.com.mymessage.util;

import android.content.Context;
import android.graphics.Color;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by hong3 on 2017-1-20.
 */

public class DanmakuUtil {

    DanmakuContext mDanmakuContext;
    Context mContext;
    DanmakuView mDanmakuView;


    public DanmakuUtil(Context context, DanmakuContext danmakuContext, DanmakuView danmakuView) {
        mContext = context;
        mDanmakuContext = danmakuContext;
        mDanmakuView = danmakuView;
    }

    //添加一条弹幕
    public void addDanmaku(String str, boolean withBorder) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);

        danmaku.text = str;
        danmaku.padding = 5;
        danmaku.textSize = DensityUtil.sp2px(mContext,20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(mDanmakuView.getCurrentTime());

        if (withBorder){
            danmaku.borderColor = Color.BLUE;
        }
        mDanmakuView.addDanmaku(danmaku);
    }




}
