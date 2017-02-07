package yang.hong3.com.mymessage.module.playvideo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.base.BaseActivity;
import yang.hong3.com.mymessage.customview.LoveLikeLayout;
import yang.hong3.com.mymessage.util.DanmakuUtil;

public class PlayLiveVideoActivity extends BaseActivity {
    private static final String TAG = "PlayLiveVideoActivity";
    private final static String PLAY_URL = "playurl";
    private final static String ICON_URL = "iconurl";
    private final static String USER_NAME = "username";
    private final static String WATCH_NUM = "watchnum";
    private final static String IS_PLAYING = "isplaying";


    String playurl, iconurl, username, watchnum;
    private SurfaceView mSurfaceView;
    private ImageView bili_anim;
    private TextView video_start_info;
    private ImageView right_play;
    private RelativeLayout bottom_layout;
    private ImageView bottom_play;
    private ImageView bottom_love;
    private ImageView bottom_fullscreen;
    private LoveLikeLayout mLikeLayout;
    private ImageView user_pic;
    private TextView user_name;
    private TextView live_num;
    private TextView focus;
    private DanmakuView mDanmakuView;
    private TextView danmakuContent;
    private Button danmakuCommit;

    private IjkMediaPlayer mIjkMediaPlayer;
    boolean isPlaying;

    DanmakuContext mDanmakuContext;
    boolean showDanmaku;
    DanmakuUtil mDanmakuUtil;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_play_live_video;
    }

    @Override
    protected void initView() {
        mSurfaceView = $(R.id.video_view);
        bili_anim = $(R.id.bili_anim);
        video_start_info = $(R.id.video_start_info);
        right_play = $(R.id.right_play);
        bottom_layout = $(R.id.bottom_layout);
        bottom_fullscreen = $(R.id.bottom_fullscreen);
        bottom_love = $(R.id.bottom_love);
        bottom_play = $(R.id.bottom_play);
        mLikeLayout = $(R.id.love_layout);
        user_pic = $(R.id.user_pic);
        user_name = $(R.id.user_name);
        live_num = $(R.id.live_num);
        focus = $(R.id.focus);
        danmakuContent = $(R.id.danmaku_content);
        danmakuCommit = $(R.id.danmaku_commit);
        mDanmakuView = $(R.id.vidio_danmuku);
        mSurfaceView.setEnabled(false);

        //读取页面传递过来的数据
        getIntentData();

        //初始化页面的默认显示状态
        initDefaultStatus();

        onclick();

        initDanmaku();

    }

    private void initDanmaku() {
        mDanmakuView.enableDanmakuDrawingCache(true);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuUtil = new DanmakuUtil(this,mDanmakuContext,mDanmakuView);
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                mDanmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });

    }

    public void generateSomeDanmaku(){
        new  Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    int time = new Random().nextInt(300);
                    String content = ""+time+time;
                    mDanmakuUtil.addDanmaku(content,false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mIjkMediaPlayer!=null && isPlaying && !mIjkMediaPlayer.isPlaying()){
            startPlay();
        }
    }


    private void initDefaultStatus() {
        right_play.setVisibility(View.VISIBLE);
        user_name.setText(username);
        live_num.setText(watchnum);
        Picasso.with(this).load(iconurl).into(user_pic);
    }


    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            fastShow(mSurfaceView, "直播房间地址传递失败");
            return;
        }
        playurl = bundle.getString(PLAY_URL);
        iconurl = bundle.getString(ICON_URL);
        username = bundle.getString(USER_NAME);
        watchnum = bundle.getString(WATCH_NUM);
    }

    /**
     * 控件监听
     */
    private void onclick() {
        //播放按钮
        right_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化等待界面
                mSurfaceView.setEnabled(true);
                right_play.setVisibility(View.GONE);
                video_start_info.setVisibility(View.VISIBLE);
                bili_anim.setVisibility(View.VISIBLE);
                AnimationDrawable anim = (AnimationDrawable) bili_anim.getBackground();
                anim.start();
                //初始化播放器
                initSurfaceView();
                //播放
                startPlay();
                bottom_play.setImageResource(R.mipmap.ic_portrait_stop);
                mDanmakuView.prepare(parser,mDanmakuContext);
            }
        });

        mSurfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottom_layout.getVisibility()!=View.VISIBLE){
                    bottom_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        bottom_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIjkMediaPlayer.isPlaying()){
                    pausPlay();
                    danmakuPause();
                    bottom_play.setImageResource(R.mipmap.ic_portrait_play);
                }else{
                    mIjkMediaPlayer.start();
                    isPlaying = true;
                    danmakuResume();
                    bottom_play.setImageResource(R.mipmap.ic_portrait_stop);
                }
            }
        });

        danmakuCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = danmakuContent.getText().toString();
                if (!TextUtils.isEmpty(str)){
                    if (mDanmakuView.isPrepared())
                        mDanmakuUtil.addDanmaku(str,true);
                }
            }
        });
    }

    /**
     * 初始化播放器
     */
    private void initSurfaceView() {
        mIjkMediaPlayer = new IjkMediaPlayer();
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mIjkMediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        mIjkMediaPlayer.setDisplay(holder);
        try {
            mIjkMediaPlayer.setDataSource(this, Uri.parse(playurl));
            mIjkMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始播放
     */
    private void startPlay() {
        mSurfaceView.setVisibility(View.VISIBLE);
        video_start_info.setVisibility(View.GONE);
        bili_anim.setVisibility(View.GONE);
        mIjkMediaPlayer.start();
        isPlaying = true;
    }

    /**
     * 暂停播放
     */
    private void pausPlay() {
        if (mIjkMediaPlayer!=null&&mIjkMediaPlayer.isPlaying()){
            mIjkMediaPlayer.pause();
        }
        isPlaying = false;
    }

    /**
     * 销毁播放组件
     */
    private void stopPlay() {
        if (mIjkMediaPlayer!=null&&mIjkMediaPlayer.isPlaying()){
            mIjkMediaPlayer.stop();
        }
        isPlaying = false;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        danmakuResume();
    }

    private void danmakuResume() {
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()){
            mDanmakuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIjkMediaPlayer!=null && mIjkMediaPlayer.isPlaying()) {
            mIjkMediaPlayer.pause();
        }

        danmakuPause();
    }

    private void danmakuPause() {
        if (mDanmakuView != null && mDanmakuView.isPrepared()){
            mDanmakuView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIjkMediaPlayer!=null && mIjkMediaPlayer.isPlaying()) {
            mIjkMediaPlayer.stop();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        showDanmaku = false;
        if (mDanmakuView != null){
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }

    public static void startActivity(Context context, String playUrl, String iconUrl, String userName, String watchNum) {
        Intent intent = new Intent(context, PlayLiveVideoActivity.class);
        intent.putExtra(PLAY_URL, playUrl);
        intent.putExtra(ICON_URL, iconUrl);
        intent.putExtra(USER_NAME, userName);
        intent.putExtra(WATCH_NUM, watchNum);
        context.startActivity(intent);
    }
}
