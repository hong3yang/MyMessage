package yang.hong3.com.mymessage.module.hotblog.view;

import java.util.List;

import yang.hong3.com.mymessage.bean.hotblog.HotblogBean;

/**
 * Created by hong3 on 2017-1-12.
 */

public interface HotblogResult {

    public void getDataSuccess(List<HotblogBean> beanList);
    public void getDataFails(String str);
}
