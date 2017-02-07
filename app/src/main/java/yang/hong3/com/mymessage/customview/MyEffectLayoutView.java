package yang.hong3.com.mymessage.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import yang.hong3.com.mymessage.R;

/**
 * Created by hong3 on 2017-1-19.
 */

public class MyEffectLayoutView extends LinearLayout{

    View rootView;
    TextView mtv_freq;
    SeekBar mSeekBar;

    public MyEffectLayoutView(Context context) {
        this(context,null);
    }

    public MyEffectLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs,0);


    }

    public MyEffectLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_effect,this,true);
        mtv_freq = (TextView)findViewById(R.id.freq);
        mSeekBar = (SeekBar)findViewById(R.id.seekbar);

    }

    public TextView setCenterFreq(String str){
        mtv_freq.setText(str);
        return mtv_freq;
    }

    public SeekBar setSeekBarProgress(int progress){

        mSeekBar.setProgress(progress);
        return mSeekBar;
    }

    public void setSeekBarMax(int max){
        mSeekBar.setMax(max);
    }

    public void setSeekBarEnable(boolean enable){
        mSeekBar.setEnabled(enable);
    }

}
