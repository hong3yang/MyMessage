package yang.hong3.com.mymessage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.bean.live.Banner;
import yang.hong3.com.mymessage.bean.live.LiveBean;
import yang.hong3.com.mymessage.customview.banner.BannerView;
import yang.hong3.com.mymessage.module.playvideo.view.PlayLiveVideoActivity;

/**
 * Created by hong3 on 2017-1-10.
 */

public class HomeLiveAdapter extends RecyclerView.Adapter {
    private static final String TAG = "HomeLiveAdapter";
    List<LiveBean> mBeanList;
    List<Banner> mBanners;
    Context mContext;
    String title = "";

    public HomeLiveAdapter(List<Banner> banners, List<LiveBean> beanList, Context context) {
        mBanners = banners;
        mBeanList = beanList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0://banner
                Log.d(TAG, "onCreateViewHolder: banners-->" + mBanners.size());
                view = LayoutInflater.from(mContext).inflate(R.layout.item_live_banner, null);
                return new BannerHolder(view);
            case 1://content
                view = LayoutInflater.from(mContext).inflate(R.layout.item_live_content, null);
                return new ContentHolder(view);
            case 2:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_live_title,null);
                return new TitleHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;   //banner
        }
        position -= 1;

        if (mBeanList.get(position).getArea_id() == 1234){
            return 2;
        }else{
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerHolder) {
            ((BannerHolder) holder).bannerView.delayTime(3).build(mBanners);
        }
        position -= 1;
        if (holder instanceof ContentHolder) {
            final LiveBean bean = mBeanList.get(position);
            Picasso.with(mContext).load(bean.getCover().getSrc()).into(((ContentHolder) holder).imageView);
            Picasso.with(mContext).load(bean.getOwner().getFace()).into(((ContentHolder) holder).icon);
            ((ContentHolder) holder).name.setText(bean.getTitle());
            ((ContentHolder) holder).number.setText(bean.getOnline() + "");
            ((ContentHolder) holder).user.setText(bean.getOwner().getName());
            ((ContentHolder) holder).cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayLiveVideoActivity.startActivity(mContext, bean.getPlayurl(), bean.getOwner().getFace(), bean.getOwner().getName(), bean.getOnline() + "");

                }
            });
        }

        if (holder instanceof TitleHolder){
            ((TitleHolder) holder).mTextView.setText(mBeanList.get(position).getArea());
        }
    }

    @Override
    public int getItemCount() {
        if (mBanners != null) {
            return mBeanList.size() + 1;
        } else {
            return mBeanList.size();
        }
    }

    public int getSpanSize(int position) {
        if(position == 0){
            return 2;
        }

        position -=1;
        if (mBeanList.get(position).getArea_id() == 1234){
            return 2;
        }else{
            return 1;
        }

    }


    class BannerHolder extends RecyclerView.ViewHolder {
        BannerView bannerView;

        public BannerHolder(View itemView) {
            super(itemView);
            bannerView = (BannerView) itemView.findViewById(R.id.live_banner);
        }
    }

    class ContentHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView icon;
        TextView name;
        TextView user;
        TextView number;
        View cardview;


        public ContentHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.live_content_img);
            icon = (ImageView) v.findViewById(R.id.live_content_icon);
            name = (TextView) v.findViewById(R.id.live_content_name);
            user = (TextView) v.findViewById(R.id.live_content_user);
            number = (TextView) v.findViewById(R.id.live_content_num);
            cardview = v.findViewById(R.id.live_cardview);
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public TitleHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.live_title);
        }
    }
}
