package services.xueqiu;

import models.UiDao;

import java.util.Comparator;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 17:29
 */
public class XQSayingComparator implements Comparator<XqStatus> {
    @Override
    public int compare(XqStatus o1, XqStatus o2) {
        long l1 = Long.parseLong(o1.getCreated_at());
        long l2 = Long.parseLong(o2.getCreated_at());

        if(l1 > l2)
            return 1;
        return -1;
    }
}
