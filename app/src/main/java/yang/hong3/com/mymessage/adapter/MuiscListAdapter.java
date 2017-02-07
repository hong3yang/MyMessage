package yang.hong3.com.mymessage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.bean.music.MusicListBean;
import yang.hong3.com.mymessage.global.Constances;
import yang.hong3.com.mymessage.module.playmusic.MusicPlayService;
import yang.hong3.com.mymessage.module.playmusic.MusicPlayingActivity;

/**
 * Created by hong3 on 2017-1-13.
 */

public class MuiscListAdapter extends RecyclerView.Adapter{
    private static final String TAG = "MuiscListAdapter";
    List<MusicListBean> mListBean;
    Context mContext;
    ArrayList<String> mList;
    int currentPosition = -1;
    int action = -1;

    public MuiscListAdapter(List<MusicListBean> listBean, Context context,ArrayList<String> list) {
        mListBean = listBean;
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_musiclist_recycler,null);

        return new MusicHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MusicHolder myholder = (MusicHolder) holder;

        final MusicListBean listBean = mListBean.get(position);
        myholder.singer.setText(listBean.getSingername());
        myholder.title.setText(listBean.getFilename());
        myholder.num.setText((position+1)+"");
        if (currentPosition == position && action == Constances.PLAY){
            myholder.play.setImageResource(R.drawable.img_appwidget91_pause_normal);
        }else{
            myholder.play.setImageResource(R.drawable.img_appwidget91_play_normal);
        }
        myholder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MusicPlayService.class);
                intent.putExtra(Constances.CURRENT_ID,position);
                Log.d(TAG, "onClick: 点击了"+mList.get(position));
                mContext.startService(intent);
            }
        });
        myholder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayingActivity.startActivity(mContext,mList);
            }
        });

    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void getMusicHash(){

    }

    @Override
    public int getItemCount() {
        return mListBean.size();
    }

    class MusicHolder extends RecyclerView.ViewHolder {
        TextView num;
        TextView title;
        TextView singer;
        ImageView play;
        CardView mCardView;

        public MusicHolder(View itemView) {
            super(itemView);
            num = (TextView) itemView.findViewById(R.id.musiclist_num);
            title = (TextView) itemView.findViewById(R.id.musiclist_title);
            singer = (TextView) itemView.findViewById(R.id.musiclist_singer);
            play = (ImageView) itemView.findViewById(R.id.musiclist_play);
            mCardView = (CardView) itemView.findViewById(R.id.musiclist_card);

        }


    }
}
