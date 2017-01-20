package services.weibo;

import models.UiDao;
import weiboclient4j.model.Status;

import java.util.Comparator;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 17:29
 */
public class StatusComparator implements Comparator<Status> {
    @Override
    public int compare(Status o1, Status o2) {
        if(o1.getId() > o2.getId())
            return -1;
        return 1;
    }
}
