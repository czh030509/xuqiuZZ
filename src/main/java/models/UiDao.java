package models;

import services.taoguba.Detail;
import services.taoguba.ReplyItem;
import services.xueqiu.XqStatus;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 11:49
 */
public class UiDao {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int id;
    private String title;
    private String created_at;
    private String description;
    private String type;
    private User user;
    private String text;
    private String url;

    public UiDao (String text, String time,  String title, String name) {
        this.setText(text);
        this.setCreated_at(time);
        this.setTitle(name);

        User user = new User();
        user.setScreen_name(name);
        this.setUser(user);
    }

    public UiDao (Detail detail) {
        this.setText(detail.getText());
        this.setCreated_at(detail.getTime());
        this.setTitle(detail.getTitle());
        this.setUrl(detail.getUrl());

        User user = new User();
        user.setScreen_name(detail.getUser());
        this.setUser(user);
    }

    public UiDao (XqStatus xqStatus) {
        this.setText(xqStatus.getText());

        Timestamp timestamp = new Timestamp(Long.parseLong(xqStatus.getCreated_at()));
        this.setCreated_at(df.format(timestamp));
        this.setTitle(xqStatus.getTitle());
        this.setDescription(xqStatus.getDescription());

        User user = new User();
        user.setScreen_name(xqStatus.getUser().getScreen_name());
        this.setUser(user);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
