package services.xueqiu;

import com.qunar.piao.ShowJob;
import models.UiDao;
import utils.base.JacksonUtils;

import java.util.*;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 17:26
 */
public class XueQiuPachong {
    private static services.xueqiu.XQSayingComparator xqSayingComparator = new XQSayingComparator();

    private long lastUpdate;
    private boolean isFirstTime = true;
    private String uid;
    private String cookie;

    public XueQiuPachong(String uid, String cookie) {
        this.uid = uid;
        this.cookie = cookie;
    }

    public void getInfos() throws Exception {
        refreshInfos(uid);
    }

    private void refreshInfos(String uid) throws Exception {
        String res = XQUrlLib.getInfos(uid, cookie, null);
        XQTimeLine XQTimeLine = JacksonUtils.unmarshalFromString(res, XQTimeLine.class);
        long created_at;

        if (XQTimeLine != null && XQTimeLine.getStatuses() != null && XQTimeLine.getStatuses().size() > 0) {
            Collections.sort(XQTimeLine.getStatuses(), xqSayingComparator);

            if (isFirstTime) {
                isFirstTime = false;
                XqStatus xqStatus = XQTimeLine.getStatuses().get(XQTimeLine.getStatuses().size() - 1);
                created_at = Long.parseLong(xqStatus.getCreated_at());

                UiDao uiDao = new UiDao(xqStatus);
                ShowJob.getMyBlockingQueue().put(uiDao);
                lastUpdate = created_at;
            } else {
                for (XqStatus xqStatus : XQTimeLine.getStatuses()) {
                    created_at = Long.parseLong(xqStatus.getCreated_at());
                    if (created_at > lastUpdate) {
                        UiDao uiDao = new UiDao(xqStatus);
                        ShowJob.getMyBlockingQueue().put(uiDao);
                        lastUpdate = created_at;
                    }
                }
            }
        } else if (XQTimeLine != null && XQTimeLine.getStatuses() != null && XQTimeLine.getStatuses().size() == 0) {
            throw new Exception("Statuses=0，cookie貌似失效了！");
        }
    }
}
