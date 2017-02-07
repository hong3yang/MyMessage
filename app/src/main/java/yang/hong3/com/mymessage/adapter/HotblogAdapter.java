package yang.hong3.com.mymessage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.bean.hotblog.HotblogBean;
import yang.hong3.com.mymessage.customview.banner.WebViewActivity;

/**
 * Created by hong3 on 2017-1-11.
 */

public class HotblogAdapter extends RecyclerView.Adapter{

    List<HotblogBean> mList;
    Context mContext;

    public HotblogAdapter(Context mContext,List<HotblogBean> list) {
        mList = list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotblog_recycler,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;

        final HotblogBean bean = mList.get(position);
        if (bean.getImages()!=null &&bean.getImages().size()>0){
            Picasso.with(mContext).load(bean.getImages().get(0)).into(myHolder.image);
        }else{
            myHolder.image.setVisibility(View.GONE);
        }

        myHolder.name.setText(bean.getWho());
        myHolder.title.setText(bean.getDesc());
        myHolder.time.setText(bean.getPublishedAt().substring(0,bean.getPublishedAt().length()-2).replace("T"," "));

        myHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.startActivity(mContext,bean.getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView time;
        TextView name;
        LinearLayout mLayout;


        public MyViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.hotblog_image);
            title = (TextView) itemView.findViewById(R.id.hotblog_title);
            time = (TextView) itemView.findViewById(R.id.hotblog_time);
            name = (TextView) itemView.findViewById(R.id.hotblog_name);
            mLayout = (LinearLayout) itemView.findViewById(R.id.hotblog_content);

        }
    }
}
