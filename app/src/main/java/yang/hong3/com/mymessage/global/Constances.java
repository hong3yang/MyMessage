package yang.hong3.com.mymessage.global;

/**
 * Created by hong3 on 2017-1-9.
 */

public class Constances {
    public static final String LIVE_BASE_URL = "http://live.bilibili.com/";
    public static final String MUSIC_BASE_URL = "http://trackercdn.kugou.com/";//获取歌曲详细信息

    public static final String MUSIC_KEY = "634c4dff57392281f791b09079743de7";//获取歌曲详细信息

    /*=======================我是分割线-->以下是音乐播放器相关=================================*/
    public static final String BROADCAST_FILTER_STATUS = "yang.hong3.com.mymessage.status.music";
    //action
    public static final int PLAY = 0x02;  //播放
    public static final int PAUSE = 0x03;  // 暂停
    public static final int STOP = 0x04;   // 停止
    public static final int NEXT = 0x05;   //下一首
    public static final int LAST = 0x06;   //上一首

    //播放模式
    public static final String PLAY_MODE = "playmode";
    public static final int SHUNXU = 0x20;
    public static final int SUIJI = 0x21;
    public static final int DANQU = 0x22;

    //key
    public static final String MUSIC_LIST = "musiclist";
    public static final String CURRENT_ID = "currentid";
    public static final String ACTION = "action";


    //
    public static final String POSITION = "position";
    public static final String PROGRESS = "progress";
    public static final String LOADPROGRESS = "loadprogress";


    /*======================音效相关===========================*/
    public static final String BROADCAST_FILTER_EFFECT= "yang.hong3.com.mymessage.music.effect";
    public static final String MUSIC_EFFECT = "musiceffect";


    public static final int CANCEL_ACOUSTIC_ECHO_TRUE = 0x10; //取消回声控制器—-开
    public static final int CANCEL_ACOUSTIC_ECHO_FALSE = 0x11; //取消回声控制器--关
    public static final int AUTONATIC_GAIN_TRUE=0x12;//自动增益控制器-开
    public static final int AUTONATIC_GAIN_FALSE = 0x13;//自动增益控制器-关
    public static final int NOISESUPPRESSOR_TRUE = 0x14;//噪音压制控制器--开
    public static final int NOISESUPPRESSOR_FALSE= 0x15;//噪音压制控制器--关

    public static final String BASSBOOST = "bass_boost";
    public static final String EQUALIZER = "equlizer";
    public static final String PRESETREVORB = "preset_revorb";

    public static final String MIN_EQ_LEVEL = "minEQLevel";
    public static final String MAX_EQ_LEVEL = "maxEQLevel";
    public static final String BRANDS = "brands";
    public static final String PRESET_NAME = "presetName";
    public static final String CENTERFREQ = "centerfreq";
    public static final String DEFAULTFREQ = "defaultfreq";
    public static final String CURRENT_PRESET = "currentpreset";
    public static final String CURRENT_BASSBOOST = "currentbassboost";



}
