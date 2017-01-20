package services.taoguba;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/2 14:41
 */
public class Detail extends ReplyItem {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String text;

    public Detail() {

    }

    public Detail(String text, ReplyItem replyItem) {
        super(replyItem.getTime(), replyItem.getUrl(), replyItem.getTitle(), replyItem.getUser());
        this.text = text;
    }
}
