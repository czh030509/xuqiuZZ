package services.taoguba;

import models.DefaultContant;
import models.ui.TipsWindow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 9:55
 */
public class ReplylPageProcessor implements PageProcessor {
    private static SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
    private boolean isFirstTime = true;
    private String lastReplyTime = "12-01 00:00";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(3000).addHeader("Cookie", DefaultContant.getTgb_cookie()).setCharset("utf-8");

    @Override
    public void process(Page page) {

        try {
            //获取tbody
            Document doc = Jsoup.parse(page.getHtml().toString());
            Element ele = doc.select("form.T1").first();
            Element tbody = ele.child(0).getElementsByTag("tbody").first();

            //循环trs，获取时间和url
            for(int i = 1;i < tbody.children().size();i ++) {
                String time = tbody.child(i).child(1).text();
                String detailUrl = tbody.child(i).child(2).select("a").first().attributes().get("href");
                String user = tbody.child(i).child(3).select("a").first().text();
                String title = tbody.child(i).child(4).select("a").first().text();

                ReplyItem replyItem = new ReplyItem(time, detailUrl, title, user);

                if (isFirstTime) {
                    TGBJob.getMyBlockingQueue().put(replyItem);
                    isFirstTime = false;
                    break;
                } else {
                    Date d1 = df.parse(time);
                    Date d2 = df.parse(lastReplyTime);
                    if (d1.after(d2)) {
                        TGBJob.getMyBlockingQueue().put(replyItem);
                    } else {
                        break;
                    }
                }
            }

            lastReplyTime = tbody.child(1).child(1).text();
        } catch (Exception e) {
            TipsWindow tipsWindow = new TipsWindow();
            tipsWindow.showError(e.getMessage());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
