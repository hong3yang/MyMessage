package yang.hong3.com.mymessage.module.playmusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.util.ArrayList;

import yang.hong3.com.mymessage.MyApplication;
import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.base.BaseFeagmentV4;
import yang.hong3.com.mymessage.customview.MyEffectLayoutView;
import yang.hong3.com.mymessage.global.Constances;

/**
 * Created by hong3 on 2017-1-18.
 */

public class MusicEffectFragment extends BaseFeagmentV4 {
    private static final String TAG = "MusicEffectFragment";
    LinearLayout mLayout;
    short minEQLevel;
    short maxEQLevel;
    short brands;
    short currentPreset;
    ArrayList<String> presetName = new ArrayList<>();
    ArrayList<Integer> freqs = new ArrayList<>();
    ArrayList<Integer> defaultFreqs = new ArrayList<>();

    EffectBroadcastReceiver mEffectReceiver;
    private short currentBassboost;

    MyEffectLayoutView mMyEffectLayoutView1;
    MyEffectLayoutView mMyEffectLayoutView2;
    MyEffectLayoutView mMyEffectLayoutView3;
    MyEffectLayoutView mMyEffectLayoutView4;
    MyEffectLayoutView mMyEffectLayoutView5;
    Spinner mSpinner;
    SeekBar mSeekBar;
    private boolean updatePreset;


    @Override
    public int getResourceId() {
        return R.layout.fragment_music_effect;
    }

    @Override
    protected void initView() {
        mEffectReceiver = new EffectBroadcastReceiver();

        mMyEffectLayoutView1 = $(R.id.effectLayoutView1);
        mMyEffectLayoutView2 = $(R.id.effectLayoutView2);
        mMyEffectLayoutView3 = $(R.id.effectLayoutView3);
        mMyEffectLayoutView4 = $(R.id.effectLayoutView4);
        mMyEffectLayoutView5 = $(R.id.effectLayoutView5);
        mSeekBar = $(R.id.bassboost_seekbar);
        mSpinner = $(R.id.spinner);
    }

    @Override
    public void initViewComplete() {
        super.initViewComplete();

        registerBroadcast();

        Intent intent = new Intent(getActivity(), MusicPlayService.class);
        intent.putExtra(Constances.MUSIC_EFFECT, 0x05);
        getActivity().startService(intent);
    }

    /**
     * 更新界面
     */
    private void updateData() {

        //重低音
        if (currentBassboost == -1) {
            mSeekBar.setProgress(0);
        } else {
            mSeekBar.setProgress(currentBassboost);
        }
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intent intent = new Intent(getActivity(), MusicPlayService.class);
                intent.putExtra(Constances.BASSBOOST, (short) progress);
                getActivity().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //音效
        if (updatePreset) {
            updatePreset = false;
            mSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, presetName));
            if (currentPreset == -1 || currentPreset == 0) {
                mSpinner.setSelection((MyApplication.myPreset == -1) ? 0: MyApplication.myPreset);
            }else{
                mSpinner.setSelection(currentPreset);
            }

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    MyApplication.myPreset = (short) position;

                    Intent intent = new Intent(getActivity(), MusicPlayService.class);
                    intent.putExtra(Constances.PRESETREVORB, position);
                    getActivity().startService(intent);

                    if (position == 0) {
                        mMyEffectLayoutView1.setSeekBarEnable(true);
                        mMyEffectLayoutView2.setSeekBarEnable(true);
                        mMyEffectLayoutView3.setSeekBarEnable(true);
                        mMyEffectLayoutView4.setSeekBarEnable(true);
                        mMyEffectLayoutView5.setSeekBarEnable(true);
                    } else {
                        mMyEffectLayoutView1.setSeekBarEnable(false);
                        mMyEffectLayoutView2.setSeekBarEnable(false);
                        mMyEffectLayoutView3.setSeekBarEnable(false);
                        mMyEffectLayoutView4.setSeekBarEnable(false);
                        mMyEffectLayoutView5.setSeekBarEnable(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        //均衡器
        mMyEffectLayoutView1.setCenterFreq(freqs.get(0) / 1000 + " Hz");
        mMyEffectLayoutView2.setCenterFreq(freqs.get(1) / 1000 + " Hz");
        mMyEffectLayoutView3.setCenterFreq(freqs.get(2) / 1000 + " Hz");
        mMyEffectLayoutView4.setCenterFreq(freqs.get(3) / 1000 + " Hz");
        mMyEffectLayoutView5.setCenterFreq(freqs.get(4) / 1000 + " Hz");

        mMyEffectLayoutView1.setSeekBarMax(maxEQLevel - minEQLevel);
        mMyEffectLayoutView2.setSeekBarMax(maxEQLevel - minEQLevel);
        mMyEffectLayoutView3.setSeekBarMax(maxEQLevel - minEQLevel);
        mMyEffectLayoutView4.setSeekBarMax(maxEQLevel - minEQLevel);
        mMyEffectLayoutView5.setSeekBarMax(maxEQLevel - minEQLevel);

        mMyEffectLayoutView1.setSeekBarProgress(defaultFreqs.get(0)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intent intent = new Intent(getActivity(), MusicPlayService.class);
                intent.putExtra(Constances.EQUALIZER, new short[]{0, (short) progress});
                getActivity().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMyEffectLayoutView2.setSeekBarProgress(defaultFreqs.get(1)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intent intent = new Intent(getActivity(), MusicPlayService.class);
                intent.putExtra(Constances.EQUALIZER, new short[]{1, (short) progress});
                getActivity().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMyEffectLayoutView3.setSeekBarProgress(defaultFreqs.get(2)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intent intent = new Intent(getActivity(), MusicPlayService.class);
                intent.putExtra(Constances.EQUALIZER, new short[]{2, (short) progress});
                getActivity().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMyEffectLayoutView4.setSeekBarProgress(defaultFreqs.get(3)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intent intent = new Intent(getActivity(), MusicPlayService.class);
                intent.putExtra(Constances.EQUALIZER, new short[]{3, (short) progress});
                getActivity().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mMyEffectLayoutView5.setSeekBarProgress(defaultFreqs.get(4)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Intent intent = new Intent(getActivity(), MusicPlayService.class);
                intent.putExtra(Constances.EQUALIZER, new short[]{4, (short) progress});
                getActivity().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constances.BROADCAST_FILTER_EFFECT);
        getActivity().registerReceiver(mEffectReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mEffectReceiver);
    }

    class EffectBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                minEQLevel = intent.getShortExtra(Constances.MIN_EQ_LEVEL, (short) -1);
                maxEQLevel = intent.getShortExtra(Constances.MAX_EQ_LEVEL, (short) -1);
                brands = intent.getShortExtra(Constances.BRANDS, (short) -1);
                currentPreset = intent.getShortExtra(Constances.CURRENT_PRESET, (short) -1);
                currentBassboost = intent.getShortExtra(Constances.CURRENT_BASSBOOST, (short) -1);
                freqs.clear();

                defaultFreqs.clear();
                freqs.addAll(intent.getIntegerArrayListExtra(Constances.CENTERFREQ));
                if (intent.getStringArrayListExtra(Constances.PRESET_NAME) != null) {
                    updatePreset = true;
                    presetName.clear();
                    presetName.addAll(intent.getStringArrayListExtra(Constances.PRESET_NAME));
                }
                defaultFreqs.addAll(intent.getIntegerArrayListExtra(Constances.DEFAULTFREQ));

                //绘制界面
                updateData();


            }

        }


    }
}



