package yang.hong3.com.mymessage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import yang.hong3.com.mymessage.R;
import yang.hong3.com.mymessage.bean.live.Banner;
import yang.hong3.com.mymessage.bean.live.EntranceIcons;
import yang.hong3.com.mymessage.bean.live.Partitions;
import yang.hong3.com.mymessage.bean.live.RecommendData;
import yang.hong3.com.mymessage.customview.banner.BannerView;


/**
 * Created by hong3 on 2016/12/1.
 */

public class LiveAppIndexAdapter extends RecyclerView.Adapter {

    private final int entrance = 0;
    private final int item = 1;
    private final int partition = 2;
    private final int banner = 3;

    List<Banner> banners;
    List<EntranceIcons> entranceIconses;
    List<Partitions> partitionses;
    List<RecommendData> recommendDatas;

    //获取在第几组中
    private int entranceSize;

    private Context context;

    private static final String TAG = "LiveAppIndexAdapter";

    public LiveAppIndexAdapter() {

    }

    public void setLiveInfo(List<Banner> banners, List<EntranceIcons> entranceIconses, List<Partitions> partitionses, List<RecommendData> recommendDatas) {
        this.banners = banners;
        this.entranceIconses = entranceIconses;
        this.partitionses = partitionses;
        this.recommendDatas = recommendDatas;
        entranceSize = 4;
    }

    private int[] entranceIconRes = new int[]{
            R.mipmap.live_home_follow_anchor,
            R.mipmap.live_home_live_center,
            R.mipmap.live_home_search_room,
            R.mipmap.live_home_all_category
    };

    private String[] entranceTitles = new String[]{
            "关注主播", "直播中心",
            "搜索直播", "全部分类"
    };

    public int getSpanSize(int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case entrance:
                return 3;
            case item:
                return 6;
            case partition:
                return 12;
            case banner:
                return 12;
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return banner;
        }
        position -= 1;
        if (position < entranceSize) {
            return entrance;
        } else if (isPartitionTitle(position)) {
            return partition;
        } else {
            return item;
        }
    }

    private boolean isPartitionTitle(int pos) {
        pos -= entranceSize;
        return (pos % 5 == 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        this.context = parent.getContext();
        switch (viewType) {
            case entrance:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_entrance, null);
                return new EntranceViewHolder(v);
            case partition:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_item, null);
                return new ItemViewHolder(v);

            case item:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_content, null);
                return new ContentViewHolder(v);
            case banner:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_live_banner, null);
                return new BannerViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        position -= 1;
        if (holder instanceof EntranceViewHolder) {
            ((EntranceViewHolder) holder).textView.setText(entranceTitles[position]);
            ((EntranceViewHolder) holder).imageView.setImageResource(entranceIconRes[position]);
            final int finalPosition = position;
            ((EntranceViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,entranceTitles[finalPosition],Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof BannerViewHolder) {
            ((BannerViewHolder) holder).bannerView.delayTime(2).build(banners);

        } else if (holder instanceof ItemViewHolder) {
            position = (position - 4) / 5;
            Picasso.with(context).load(partitionses.get(position).getPartition().getSub_icon().getSrc()).into(((ItemViewHolder) holder).image);
            ((ItemViewHolder) holder).name.setText(partitionses.get(position).getPartition().getName());
            ((ItemViewHolder) holder).number.setText("" + partitionses.get(position).getPartition().getCount());


        } else if (holder instanceof ContentViewHolder) {
//            position = (position - 4);
            int i = position - 1 - entranceSize - getItemPosition(position) * 5;
            final Partitions.LivesBean bean = partitionses.get(getItemPosition(position)).getLives().get(i);
            Picasso.with(context).load(bean.getCover().getSrc()).into(((ContentViewHolder) holder).imageView);
            Picasso.with(context).load(bean.getOwner().getFace()).into(((ContentViewHolder) holder).icon);
            ((ContentViewHolder) holder).name.setText(bean.getTitle());
            ((ContentViewHolder) holder).number.setText(bean.getOnline() + "");
            ((ContentViewHolder) holder).user.setText(bean.getOwner().getName());
            ((ContentViewHolder) holder).cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    LiveDitailActivity.startActivity((Activity) context,
//                            bean.getRoom_id(),
//                            bean.getTitle(),
//                            bean.getOnline(),
//                            bean.getOwner().getFace(),
//                            bean.getOwner().getName(),
//                            bean.getOwner().getMid(),
//                            bean.getPlayurl()
//                    );
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        if (partitionses != null) {
            return 1 + entranceIconRes.length + partitionses.size() * 5;

        } else {
            return 0;
        }


    }

    /**
     * 获取当前Item在第几组中
     */
    private int getItemPosition(int pos) {
        pos -= entranceSize;
        return pos / 5;
    }

    class EntranceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public EntranceViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.live_entrance_image);
            textView = (TextView) itemView.findViewById(R.id.live_entrance_text);
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        BannerView bannerView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            bannerView = (BannerView) itemView.findViewById(R.id.live_banner);
        }

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView number;

        public ItemViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.live_item);
            name = (TextView) itemView.findViewById(R.id.live_text);
            number = (TextView) itemView.findViewById(R.id.live_num);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView icon;
        TextView name;
        TextView user;
        TextView number;
        View cardview;


        public ContentViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.live_content_img);
            icon = (ImageView) v.findViewById(R.id.live_content_icon);
            name = (TextView) v.findViewById(R.id.live_content_name);
            user = (TextView) v.findViewById(R.id.live_content_user);
            number = (TextView) v.findViewById(R.id.live_content_num);
            cardview = v.findViewById(R.id.live_cardview);
        }
    }


}
