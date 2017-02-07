package yang.hong3.com.mymessage.module.playmusic;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.base.BaseActivity;
import yang.hong3.com.mymessage.bean.music.MusicListBean;
import yang.hong3.com.mymessage.global.Constances;
import yang.hong3.com.mymessage.module.playmusic.presenter.MusicPlayingPresenter;

public class MusicPlayingActivity extends BaseActivity implements MusicBroadcastReceiver.BroadcastResult {
    private static final String TAG = "MusicPlayingActivity";
    public static final String MUSIC_INFO = "musicInfo";
    public static final String LIST = "list";

    TextView tv_mProgress,tv_mTotalTime;
    SeekBar mSeekBar;
    ImageView iv_prev,iv_play,iv_next,iv_playMode;
    Toolbar mToolbar;

    int[] playModeImage = {R.drawable.img_appwidget91_playmode_repeat_all,R.drawable.img_appwidget91_playmode_shuffle,R.drawable.img_appwidget91_playmode_repeat_current};
    int playMode=0;
    int currentPosition=-1;
    int mAction=-1;

    List<String> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_lrc;
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        if(intent != null){
            mList = intent.getStringArrayListExtra(LIST);
        }

        tv_mProgress = $(R.id.progress_time);
        tv_mTotalTime = $(R.id.total_time);
        mSeekBar = $(R.id.seekbar);
        iv_prev = $(R.id.music_prev);
        iv_play = $(R.id.music_play);
        iv_next = $(R.id.music_next);
        iv_playMode = $(R.id.music_play_mode);
        mToolbar = $(R.id.lrc_toolbar);

        registerBroadcast();
        getSupportFragmentManager().beginTransaction().replace(R.id.music_content,new MusicEffectFragment()).commit();

        click();
    }

    private void click() {
        //上一首
        iv_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setServiceAction(Constances.ACTION,Constances.LAST);
            }
        });

        //下一首
        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setServiceAction(Constances.ACTION,Constances.NEXT);
            }
        });

        //播放暂停
        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setServiceAction(Constances.CURRENT_ID,currentPosition);
            }
        });

        //播放模式
        iv_playMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMode = (playMode+1)%3;
                iv_playMode.setImageResource(playModeImage[playMode]);
                switch (playMode){
                    case 0:
                        setServiceAction(Constances.PLAY_MODE,Constances.SHUNXU);
                        break;
                    case 1:
                        setServiceAction(Constances.PLAY_MODE,Constances.SUIJI);
                        break;
                    case 2:
                        setServiceAction(Constances.PLAY_MODE,Constances.DANQU);
                        break;
                }

            }
        });
    }

    public void setServiceAction(String key,int value){
        Intent intent = new Intent(this, MusicPlayService.class);
        intent.putExtra(key,value);
        startService(intent);
    }


    MusicBroadcastReceiver receiver;
    public void registerBroadcast(){
        receiver = new MusicBroadcastReceiver(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constances.BROADCAST_FILTER_STATUS);
        registerReceiver(receiver,filter);
    }


    public static void startActivity(Context context,ArrayList<String> mlist){
        Intent intent = new Intent(context,MusicPlayingActivity.class);
        intent.putStringArrayListExtra(LIST,mlist);
        context.startActivity(intent);
    }

    @Override
    public void getResult(int position, int progress, int loadProgress, int action) {
        if (currentPosition != position){
            //更换新的歌曲
            currentPosition = position;
            MusicListBean bean = MusicPlayingPresenter.getMusicInfo(this,mList.get(position));
            mSeekBar.setMax(bean.getDuration());
            tv_mTotalTime.setText(formateDuration(bean.getDuration()));
            mToolbar.setTitle(bean.getFilename());
            mToolbar.setSubtitle(bean.getSingername());
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (mSeekBar.getMax()>0){
            mSeekBar.setSecondaryProgress(loadProgress);
            mSeekBar.setProgress(progress);
            tv_mProgress.setText(formateDuration(progress));
        }

        if (mAction != action){
            mAction = action;
            if (mAction == Constances.PLAY){
                iv_play.setImageResource(R.drawable.img_appwidget91_pause_normal);
            }else{
                iv_play.setImageResource(R.drawable.img_appwidget91_play_normal);
            }
        }
    }

    public String formateDuration(int duration){
        return (duration/1000/60) +":"+(duration/1000%60);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
