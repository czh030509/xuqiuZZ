package services.taoguba;

import com.qunar.piao.ShowJob;
import models.DefaultContant;
import models.UiDao;
import models.User;
import models.ui.TipsWindow;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 9:54
 */
public class DetailPageProcessor implements PageProcessor {
    private UiDao uiDao;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(3000).addHeader("Cookie", DefaultContant.getTgb_cookie()).setCharset("utf-8");

    private ReplyItem replyItem;
    private String item;

    public DetailPageProcessor(ReplyItem replyItem) {
        this.replyItem = replyItem;
        this.item = "reply" + replyItem.getUrl().split("#")[1];
    }

    @Override
    public void process(Page page) {
        String xpath = "//p[@id='" + this.item + "']/html()";
        String text = page.getHtml().xpath(xpath).get();

        try {
            if (StringUtils.isBlank(text)) {
                Thread.sleep(20 * 1000);
                TGBJob.getMyBlockingQueue().put(replyItem);
                return;
            }

            Detail detail = new Detail(text, this.replyItem);
            uiDao = new UiDao(detail);
            ShowJob.getMyBlockingQueue().put(uiDao);
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
