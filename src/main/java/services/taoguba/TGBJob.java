package services.taoguba;

import com.google.common.collect.Maps;
import com.qunar.piao.MainJob;
import com.qunar.piao.ShowJob;
import models.DefaultContant;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import services.xueqiu.XueQiuPachong;
import sun.security.provider.ConfigFile;
import us.codecraft.webmagic.Spider;
import utils.base.HttpClientUtils;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/1 19:46
 */
public class TGBJob implements Runnable {
    private long times = 0;
    private int period = DefaultContant.getPeriod();

    private Map<String, ReplylPageProcessor> pachongs;

    private static java.util.Timer timer;
    private static BlockingQueue<ReplyItem> myBlockingQueue = new ArrayBlockingQueue<ReplyItem>(50);

    public static BlockingQueue<ReplyItem> getMyBlockingQueue() {
        return myBlockingQueue;
    }

    public void init() {
        pachongs = Maps.newHashMap();

        String uids = DefaultContant.getTgb_userid();
        String[] ids = uids.split(",");

        for (int i = 0; i < ids.length; i++) {
            String url = "http://www.taoguba.com.cn/moreReply?userID=" + ids[i];
            ReplylPageProcessor pachong = new ReplylPageProcessor();

            pachongs.put(url, pachong);
        }
    }

    public TGBJob() {
        this.init();
    }

    @Override
    public void run() {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                times++;
                MainJob.getAppWindow().addMessage("第 " + times + " 次扫描--淘股吧");

                for (Map.Entry<String, ReplylPageProcessor> entry : pachongs.entrySet()) {
                    Spider.create(entry.getValue())
                            .addUrl(entry.getKey())
                            .thread(5)
                            .run();
                }
            }
        }, 0, period * 1000);

        while (MainJob.isStart()) {
            try {
                ReplyItem replyItem = myBlockingQueue.take();

                Spider.create(new DetailPageProcessor(replyItem))
                        .addUrl(replyItem.getUrl())
                        .thread(5)
                        .run();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    public void cancelTimer() {
        timer.cancel();
        myBlockingQueue.clear();
    }
}
