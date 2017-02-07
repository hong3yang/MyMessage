package yang.hong3.com.mymessage.module.playmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.NoiseSuppressor;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import yang.hong3.com.mymessage.global.Constances;

/**
 * Created by hong3 on 2017-1-14.
 */

public class MusicPlayService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
    public static final String PATH = "path";


    String filePath;

    private static final String TAG = "MusicPlayService";
    MediaPlayer mMediaPlayer;

    boolean isPrepared;
    int currentPosition = -1;
    int currentProgress;
    int loadProgress;
    int action = -1;
    int playMode=Constances.SHUNXU;
    boolean isStop = true;
    List<String> musicLists = new ArrayList<>();
    Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    //首次调用startService会回调此方法  初始化数据
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化播放器
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();

        //注册播放器的监听事件
        //播放完成的监听
        mMediaPlayer.setOnCompletionListener(this);
        //缓冲进度的监听
        mMediaPlayer.setOnBufferingUpdateListener(this);
        //准备就绪的监听
        mMediaPlayer.setOnPreparedListener(this);

        //初始化音效
        initEffect();
        //注册广播（视需要处理）

    }

    //每次调用startService 会回调本方法，用于传递数据
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //收到数据后，轮询数据，完成相应操作
        Bundle b = intent.getExtras();
        if (b == null) {
            Log.d(TAG, "onReceive: bundle is null");
            return super.onStartCommand(intent, flags, startId);
        }

        //获取歌曲列表信息
        if (b.getStringArrayList(Constances.MUSIC_LIST) != null) {
            musicLists.clear();
            musicLists.addAll(b.getStringArrayList(Constances.MUSIC_LIST));
        }

        //获取当前需要播放的歌曲id
        if (b.getInt(Constances.CURRENT_ID, -1) != -1) {
            if (b.getInt(Constances.CURRENT_ID) != currentPosition) {
                currentPosition = b.getInt(Constances.CURRENT_ID);
                startPlay();
            } else if (action == Constances.PLAY) {
                pause();
            } else {
                play();

            }
        }

        //播放模式
        if(b.getInt(Constances.PLAY_MODE,-1) != -1){
            playMode = b.getInt(Constances.PLAY_MODE);
        }

        //获取动作信息
        if (b.getInt(Constances.ACTION, -1) != -1) {
            int action = b.getInt(Constances.ACTION);
            switch (action) {
                case Constances.LAST:
                    last();
                    break;
                case Constances.NEXT:
                    next();
                    break;
                case Constances.PAUSE:
                    pause();
                    break;
                case Constances.PLAY:
                    play();
                    break;
                case Constances.STOP:
                    stop();
                    break;
            }
        }

        if (b.getInt(Constances.MUSIC_EFFECT, -1) != -1){
            switch (b.getInt(Constances.MUSIC_EFFECT)){
                case 0x05:
                    sendEffectBroadCast(true);
                    break;
                case Constances.CANCEL_ACOUSTIC_ECHO_TRUE:
                    setAcousticEchoCanceler(true);
                    break;
                case Constances.CANCEL_ACOUSTIC_ECHO_FALSE:
                    setAcousticEchoCanceler(false);
                    break;
                case Constances.AUTONATIC_GAIN_FALSE:
                    setAutomaticGainControler(false);
                    break;
                case Constances.AUTONATIC_GAIN_TRUE:
                    setAutomaticGainControler(true);
                    break;
                case Constances.NOISESUPPRESSOR_FALSE:
                    setNoiseSuppressor(false);
                    break;
                case Constances.NOISESUPPRESSOR_TRUE:
                    setNoiseSuppressor(true);
                    break;
            }
        }


        if (b.getShort(Constances.BASSBOOST, (short) 1024) !=1024){
            setBassboost(b.getShort(Constances.BASSBOOST));
        }

        if (b.getInt(Constances.PRESETREVORB,-1) != -1){

            setPresetRevorb((short) b.getInt(Constances.PRESETREVORB));
        }

        if (b.getShortArray(Constances.EQUALIZER) != null){
            setBandsLevel(b.getShortArray(Constances.EQUALIZER)[0],
                    b.getShortArray(Constances.EQUALIZER)[1]);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = false;
        if (thread != null){
            thread = null;
        }
    }

    /**
     * 子线程发送广播
     */
    public void startTimer() {
        if (thread == null) {
            thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        currentProgress = mMediaPlayer.getCurrentPosition();
                        sendBroadCast(currentPosition, currentProgress, loadProgress);
                    }
                }
            };
            thread.start();
        }

    }


    ///////////=======================播放状态控制部分======================================

    /**
     * 开始播放
     */
    public void startPlay() {
        isPrepared = false;
        mMediaPlayer.reset();  //重置播放器

        if (!setDataResource()) {
            return;
        }
        startTimer();
        mMediaPlayer.prepareAsync();
    }

    /**
     * 设置播放文件路径
     * @return
     */
    private boolean setDataResource() {
        filePath = musicLists.get(currentPosition);
        if (filePath.startsWith("/")) {
            //本地数据
            try {
                mMediaPlayer.setDataSource(filePath);
            } catch (IOException e) {
                Log.d(TAG, "setDataResource: " + e.getMessage());
                return false;
            }
        } else {
            //网络数据

//            RetrofitUtil.createMusic(MusicService.class, Constances.MUSIC_BASE_URL).getMusicPlayinfo(musicLists.get(currentPosition),
//                    Constances.MUSIC_KEY, "3", "6", "1").enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    MusicInfoBean infoBean = JSON.parseObject(response.body(), MusicInfoBean.class);
//                    try {
//                        mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(infoBean.getUrl()));
//                    } catch (IOException e) {
//                        Log.d(TAG, "setDataResource: " + e.getMessage());
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable throwable) {
//                    Log.d(TAG, "setDataResource: " + throwable.getMessage());
//                }
//            });

        }

        return true;
    }

    /**
     * 停止
     */
    private void stop() {
        action = Constances.STOP;
        isStop = true;
        mMediaPlayer.stop();
    }

    /**
     * 播放
     */
    private void play() {
        if (isPrepared) {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
                action = Constances.PLAY;
                isStop = false;
            }
        } else {
            startPlay();
        }

    }

    /**
     * 暂停
     */
    private void pause() {
        isStop = true;
        action = Constances.PAUSE;
        mMediaPlayer.pause();
    }

    /**
     * 下一首
     */
    private void next() {

        if (playMode == Constances.SHUNXU){
            //顺序
            currentPosition++;
            if (currentPosition >= musicLists.size()) {
                currentPosition = 0;
            }
        }else if(playMode == Constances.SUIJI){
            //随机
            currentPosition = (currentPosition + new Random().nextInt(musicLists.size()))%musicLists.size();
        }else{
            //单曲

        }

        startPlay();
    }

    /**
     * 上一首
     */
    private void last() {
        if (playMode == Constances.SHUNXU){
            //顺序
            currentPosition--;
            if (currentPosition < 0) {
                currentPosition = musicLists.size() - 1;
            }
        }else if(playMode == Constances.SUIJI){
            //随机
            currentPosition = (currentPosition + new Random().nextInt(musicLists.size()))%musicLists.size();
        }else{
            //单曲

        }
        startPlay();
    }


    /**
     * 发送广播，更新状态
     */
    public void sendBroadCast(int position, int progress, int loadProgress) {
        Intent intent = new Intent();
        intent.setAction(Constances.BROADCAST_FILTER_STATUS);
        intent.putExtra(Constances.POSITION, position);
        intent.putExtra(Constances.PROGRESS, progress);
        intent.putExtra(Constances.ACTION, action);
        intent.putExtra(Constances.LOADPROGRESS, loadProgress);
        sendBroadcast(intent);
    }

    /**
     * 准备就绪的回调
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        action = Constances.PLAY;
        isPrepared = true;
        isStop = false;

    }

    /**
     * 播放完成
     *
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        //开始下一首
        next();
    }

    /**
     * 缓冲进度的回调
     *
     * @param mp
     * @param percent
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadProgress = percent;
    }


    ///=========================以下为音效相关==================================

    AcousticEchoCanceler mCanceler;   //取消回声控制器
    AutomaticGainControl mControl;    //自动增益控制器
    NoiseSuppressor mNoiseSuppressor; //噪声抑制控制器

    Equalizer mEqualizer;  //均衡器
    BassBoost mBassBoost;   //重低音控制器
    PresetReverb mPresetReverb;//预设音场控制器

    short minEQLevel;
    short maxEQLevel;
    short brands;

    ArrayList<String> presetName = new ArrayList<>();
    ArrayList<Integer> freqs=new ArrayList<>();
    ArrayList<Integer> defaultFreqs = new ArrayList<>();



    private void initEffect(){
        initEqualizer();
        initBassboost();
        initPresetRevorb();
    }


    /**
     * 设置回声控制器
     * @param enable
     */
    private void setAcousticEchoCanceler(boolean enable){
        if (mCanceler == null){
            mCanceler = AcousticEchoCanceler.create(mMediaPlayer.getAudioSessionId());
            mCanceler.setEnabled(enable);
        }
    }

    /**
     * 设置自动增益控制器
     * @param enable
     */
    private void setAutomaticGainControler(boolean enable){
        if (mControl == null){
           mControl = AutomaticGainControl.create(mMediaPlayer.getAudioSessionId());
            mControl.setEnabled(enable);
        }


    }

    /**
     * 设置噪音控制器
     * @param enable
     */
    private void setNoiseSuppressor(boolean enable){
        if (mNoiseSuppressor == null){
            mNoiseSuppressor = NoiseSuppressor.create(mMediaPlayer.getAudioSessionId());
            mNoiseSuppressor.setEnabled(enable);
        }
    }

    /**
     * 初始化均衡器
     */
    private void initEqualizer(){
        if (mEqualizer == null) {
            mEqualizer = new Equalizer(0, mMediaPlayer.getAudioSessionId());
            //启动均衡器效果
            mEqualizer.setEnabled(true);
        }
        //获取均衡器支持的最大最小值
        minEQLevel = mEqualizer.getBandLevelRange()[0];
        maxEQLevel = mEqualizer.getBandLevelRange()[1];
        //获取均衡器支持的所有频率
        brands = mEqualizer.getNumberOfBands();


    }

    /**
     * 设置均衡器的值
     * @param band   频率
     * @param level  对应值
     */
    private void setBandsLevel(short band,short level){
        mEqualizer.setBandLevel(band,level);
    }

    /**
     * 初始化重低音控制器
     */
    private void initBassboost(){
        if (mBassBoost == null){
            mBassBoost = new BassBoost(0,mMediaPlayer.getAudioSessionId());
            mBassBoost.setEnabled(true);
        }


    }

    /**
     * 设置重低音控制器的值
     * @param strenth
     */
    private void setBassboost(short strenth){

        mBassBoost.setStrength(strenth);

    }

    /**
     * 初始化预设音场控制器
     */
    private void initPresetRevorb(){
        if (mPresetReverb == null){
            mPresetReverb = new PresetReverb(0,mMediaPlayer.getAudioSessionId());
            mPresetReverb.setEnabled(true);
        }


    }

    /**
     * 设置预设音场的值
     * @param preset
     */
    private void setPresetRevorb(short preset){

        mEqualizer.usePreset(preset);
        sendEffectBroadCast(false);
    }

    /**
     * 发送广播，更新状态
     */
    public void sendEffectBroadCast(boolean isNeedPresetName) {

        updateData();

        Intent intent = new Intent();
        intent.setAction(Constances.BROADCAST_FILTER_EFFECT);
        intent.putExtra(Constances.MIN_EQ_LEVEL,minEQLevel);
        intent.putExtra(Constances.MAX_EQ_LEVEL,maxEQLevel);
        intent.putExtra(Constances.BRANDS,brands);
        intent.putExtra(Constances.CURRENT_BASSBOOST,mBassBoost.getRoundedStrength());
        intent.putExtra(Constances.CURRENT_PRESET,mEqualizer.getCurrentPreset());
        intent.putIntegerArrayListExtra(Constances.CENTERFREQ,freqs);
        intent.putIntegerArrayListExtra(Constances.DEFAULTFREQ,defaultFreqs);
        if (isNeedPresetName)
            intent.putStringArrayListExtra(Constances.PRESET_NAME,presetName);
        sendBroadcast(intent);
    }

    private void updateData() {
        presetName.clear();
        freqs.clear();
        defaultFreqs.clear();

        for (short i = 0; i < brands; i++) {
            freqs.add(mEqualizer.getCenterFreq(i));
            defaultFreqs.add((int) mEqualizer.getBandLevel(i));
        }
        for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {
            presetName.add(mEqualizer.getPresetName(i));
        }
    }


}
