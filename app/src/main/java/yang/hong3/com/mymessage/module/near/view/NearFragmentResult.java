package yang.hong3.com.mymessage.module.near.view;

import java.util.List;

import yang.hong3.com.mymessage.bean.near.NearBean;

/**
 * Created by hong3 on 2017-1-22.
 */

public interface NearFragmentResult {

    public void getNearListSuccess(int currentPage,
                                   int totalPage, List<NearBean> nearBeanList);

    public void getNearListFails(String str);
}
