package yang.hong3.com.mymessage.module.live.view;

import java.util.List;

import yang.hong3.com.mymessage.bean.live.Banner;
import yang.hong3.com.mymessage.bean.live.Partitions;
import yang.hong3.com.mymessage.bean.live.RecommendData;

/**
 * Created by hong3 on 2017-1-10.
 */

public interface HomeLiveResult  {

    public void getDataSuccess(List<Banner> banners,List<Partitions> partitionses,RecommendData recommendData);
    public void getDataFails(String str);
}
