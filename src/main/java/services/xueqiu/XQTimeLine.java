package services.xueqiu;

import models.UiDao;

import java.util.List;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 11:49
 */
public class XQTimeLine {
    private int counter;
    private int total;
    private int page;
    private int maxPage;
    private List<XqStatus> statuses;

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<XqStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<XqStatus> statuses) {
        this.statuses = statuses;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
