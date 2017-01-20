package services.taoguba;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 14:41
 */
public class ReplyItem {
    private String time;
    private String url;
    private String title;
    private String user;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ReplyItem() {

    }

    public ReplyItem(String time, String url, String title, String user) {
        this.time = time;
        this.url = url;
        this.title = title;
        this.user = user;
    }
}
