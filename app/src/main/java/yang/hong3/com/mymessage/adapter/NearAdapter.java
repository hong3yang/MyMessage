package yang.hong3.com.mymessage.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.bean.near.NearBean;
import yang.hong3.com.mymessage.module.amap.BaseMapActivity;

/**
 * Created by hong3 on 2017-1-22.
 */

public class NearAdapter extends RecyclerView.Adapter {
    private static final String TAG = "NearAdapter";
    Context mContext;
    List<NearBean> mNearBeen;

    public NearAdapter(Context context, List<NearBean> nearBeen) {
        mContext = context;
        mNearBeen = nearBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_near_recycler,null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myholder = (MyViewHolder) holder;

        myholder.mName.setText(mNearBeen.get(position).getName());
        myholder.mAddress.setText(mNearBeen.get(position).getAddress());
        myholder.mDistance.setText(formateDistance(mNearBeen.get(position).getDistance()));
        myholder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseMapActivity.startActivity(mContext,Double.parseDouble(mNearBeen.get(position).getLat()),Double.parseDouble(mNearBeen.get(position).getLon()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNearBeen.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mAddress;
        TextView mDistance;
        CardView mCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.oil_name);
            mAddress = (TextView) itemView.findViewById(R.id.oil_address);
            mDistance = (TextView) itemView.findViewById(R.id.oil_distance);
            mCardView = (CardView) itemView.findViewById(R.id.near_card);
        }
    }

    public String formateDistance(int dis){
        Log.d(TAG, "formateDistance: Distance-->"+dis);
        if (dis <= 1000){
            return dis+" m";
        }
        if(dis <10000){
            return String.format("%.2f km",(float)dis/1000);
        }
        return "/";
    }

}
