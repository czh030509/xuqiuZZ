package services.weibo;

import com.qunar.piao.MainJob;
import com.qunar.piao.ShowJob;
import models.DefaultContant;
import models.UiDao;
import models.User;
import models.ui.TipsWindow;
import weiboclient4j.model.Timeline;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import weiboclient4j.model.Status;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 17:26
 */
public class XLJob implements Runnable {
    private static long times = 0;
    private static long nextCursor = 0;
    private static StatusComparator statusComparator = new StatusComparator();

    private Timer timer;
    private String uids = DefaultContant.getXl_ids();
    private int period = DefaultContant.getPeriod();

    public XLJob() {

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

    public void cancelTimer() {
        timer.cancel();
    }

    private void getInfos() {
        try {
            times++;
            MainJob.getAppWindow().addMessage("第 " + times + " 次扫描 -- 微博");

            Xlweibo weibo = new Xlweibo();
            Timeline timeline = weibo.getTimeline(nextCursor);
            Collections.sort(timeline.getStatuses(), statusComparator);

            if (timeline != null) {
                if (timeline.getStatuses() != null && timeline.getStatuses().size() > 0) {
                    nextCursor = timeline.getStatuses().get(0).getId();

                    for (Status status : timeline.getStatuses()) {
                        if (uids.contains(status.getUser().getIdstr())) {
                            String name = status.getUser().getScreenName();
                            String text = status.getText();
                            String time = status.getCreatedAt().toString();

                            UiDao uiDao = new UiDao(text, time, name, name);
                            try {
                                ShowJob.getMyBlockingQueue().put(uiDao);
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            TipsWindow tipsWindow = new TipsWindow();
            tipsWindow.showError(ex.getMessage());
        }
    }
}
