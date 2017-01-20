package services.xueqiu;

import com.qunar.piao.MainJob;
import com.qunar.piao.ShowJob;
import models.DefaultContant;
import models.UiDao;
import models.ui.TipsWindow;
import utils.base.JacksonUtils;

import java.util.*;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 17:26
 */
public class XueQiuJob implements Runnable {
    private static services.xueqiu.XQSayingComparator xqSayingComparator = new XQSayingComparator();
    private static List<XueQiuPachong> pachongs;

    private String uids = DefaultContant.getUserid();
    private String cookie = DefaultContant.getCookie();
    private int period = DefaultContant.getPeriod();
    private long times = 0;
    private java.util.Timer timer;

    private void init() {
        pachongs = new ArrayList<XueQiuPachong>();
        String[] ids = uids.split(",");

        for(int i=0;i<ids.length;i++) {
            XueQiuPachong pachong = new XueQiuPachong(ids[i],cookie);
            pachongs.add(pachong);
        }
    }

    public XueQiuJob() {
        this.init();
    }

    @Override
    public void run() {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                getInfos();
            }
        }, 0, period * 1000);

        while (MainJob.isStart()) {
            try {
                Thread.sleep(30 * 1000);
            } catch (Exception e) {

            }
        }
    }

    private void getInfos() {
        try {
            times++;
            MainJob.getAppWindow().addMessage("第 " + times + " 次扫描--雪球");

            String[] ids = uids.split(",");
            if (ids.length < 1 | ids.length > DefaultContant.getNumLimit()) {
                throw new Exception("追踪雪球人数超过5人！");
            }

            for(int i=0;i<ids.length;i++) {
                pachongs.get(i).getInfos();
            }
        } catch (Exception e) {
            TipsWindow tipsWindow = new TipsWindow();
            tipsWindow.showError(e.getMessage());
        }
    }

    public void cancelTimer() {
        timer.cancel();
    }
}
