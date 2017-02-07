package yang.hong3.com.mymessage.module.music.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.adapter.MuiscListAdapter;
import yang.hong3.com.mymessage.base.BaseFeagmentV4;
import yang.hong3.com.mymessage.bean.music.MusicListBean;
import yang.hong3.com.mymessage.global.Constances;
import yang.hong3.com.mymessage.module.music.presenter.MusicPresenter;
import yang.hong3.com.mymessage.module.playmusic.MusicBroadcastReceiver;
import yang.hong3.com.mymessage.module.playmusic.MusicPlayService;

/**
 * Created by hong3 on 2017-1-9.
 */

public class MusicFragment extends BaseFeagmentV4 implements MusicResult, MusicBroadcastReceiver.BroadcastResult {
    private static final String TAG = "MusicFragment";
    MusicPresenter mMusicPresenter;

   RecyclerView mRecyclerView;
    int mCurrentPosition=-1;


    List<MusicListBean> mListBean = new ArrayList<>();

    MuiscListAdapter mMuiscListAdapter;

    MusicBroadcastReceiver mReceiver;
    private ArrayList<String> mList = new ArrayList<>();
    private int action=-1;

    @Override
    protected void initView() {
        mMusicPresenter = new MusicPresenter(this);
        mRecyclerView = $(R.id.musiclist_recycler);

        mMuiscListAdapter = new MuiscListAdapter(mListBean,getActivity(),mList);
        mRecyclerView.setAdapter(mMuiscListAdapter);
        registerBroadcaster();
    }

    public void registerBroadcaster(){
        mReceiver = new MusicBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter(Constances.BROADCAST_FILTER_STATUS);
        getActivity().registerReceiver(mReceiver,intentFilter);
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_main_music;
    }


    public static MusicFragment newInstance() {
        return new MusicFragment();
    }

    @Override
    public void initViewComplete() {
        super.initViewComplete();
        isPrepare = true;
        lazyload();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void lazyload() {
        super.lazyload();
        if (!isPrepare){
            return;
        }

        mMusicPresenter.getLocalMusicList(getActivity());
    }

    @Override
    public void getMusicListSuccess(List<MusicListBean> list) {
        mListBean.clear();
        mListBean.addAll(list);
        Intent intent = new Intent(getActivity(), MusicPlayService.class);
        mList.clear();
        for (int i = 0; i < mListBean.size(); i++) {
            mList.add(mListBean.get(i).getHash());
        }
        intent.putExtra(Constances.MUSIC_LIST,mList);
        getActivity().startService(intent);
        mMuiscListAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMusicListFails(String str) {
        fastShow(str);
    }

    @Override
    public void getResult(int position, int progress, int loadProgress,int action) {
        if (mCurrentPosition != position || this.action != action){
            mCurrentPosition = position;
            mMuiscListAdapter.setCurrentPosition(position);
            mMuiscListAdapter.setAction(action);
            mMuiscListAdapter.notifyDataSetChanged();
        }

    }
}
