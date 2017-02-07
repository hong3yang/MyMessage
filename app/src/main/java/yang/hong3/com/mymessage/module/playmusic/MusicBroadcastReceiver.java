package yang.hong3.com.mymessage.module.playmusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import yang.hong3.com.mymessage.global.Constances;

/**
 * Created by hong3 on 2017-1-16.
 */

public class MusicBroadcastReceiver extends BroadcastReceiver {


private static final String TAG = "MusicBroadcastReceiver";
    int position;
    int progress;
    int loadProgress;
    int action;
    BroadcastResult mBroadcastResult;

    public MusicBroadcastReceiver(BroadcastResult broadcastResult) {
        mBroadcastResult = broadcastResult;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        if (b == null) {
            return;
        }

        position = b.getInt(Constances.POSITION);
        progress = b.getInt(Constances.PROGRESS);
        loadProgress = b.getInt(Constances.LOADPROGRESS);
        action = b.getInt(Constances.ACTION);
        
        mBroadcastResult.getResult(position,progress, loadProgress, action);
    }

    public interface BroadcastResult{
        public void getResult(int position,int progress,int loadProgress,int action);
    }
}
