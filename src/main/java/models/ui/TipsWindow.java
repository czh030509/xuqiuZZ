package models.ui;

import models.UiDao;
import org.apache.commons.lang.StringUtils;
import utils.base.ExchangeHtml;
import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 14:38
 */
public class TipsWindow extends JFrame {
    private JTextArea jta;
    private JScrollPane jscrollPane;
    private Container contentPane;
    private UiDao uiDao;

    private void init() {
        this.setVisible(false);
        this.setSize(280, 200);

        jta = new JTextArea(10, 15);
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        this.jta.setEditable(false);
        jta.setTabSize(4);
        jta.setLineWrap(true);// 激活自动换行功能
        jta.setWrapStyleWord(true);// 激活断行不断字功能

        jscrollPane = new JScrollPane(jta);
        contentPane.add(jscrollPane, BorderLayout.CENTER);

        int windowWidth = this.getWidth();                    //获得窗口宽
        int windowHeight = this.getHeight();                  //获得窗口高

        Toolkit kit = Toolkit.getDefaultToolkit();             //定义工具包
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
        int taskBarHeight = screenInsets.bottom;

        Dimension screenSize = kit.getScreenSize();            //获取屏幕的尺寸
        int screenWidth = screenSize.width;                    //获取屏幕的宽
        int screenHeight = screenSize.height;                  //获取屏幕的高
        this.setLocation(screenWidth - windowWidth, screenHeight - windowHeight - taskBarHeight);//设置窗口居中显示
    }

    public TipsWindow(UiDao uiDao) {
        this.init();
        this.uiDao = uiDao;
    }

    public TipsWindow() {
        this.init();
    }

    public void showError(String error) {
        this.setTitle("异常错误");
        jta.setText(error);
        jta.append("\r\n");
        jta.append("请联系开发进行修改！");

        this.setVisible(true);
    }

    public void showMessage() {
        this.setTitle(this.uiDao.getUser().getScreen_name());

        if(StringUtils.isNotBlank(this.uiDao.getCreated_at())) {
            jta.setText("时间: " + this.uiDao.getCreated_at());
            jta.append("\r\n");
        }

        if (StringUtils.isNotBlank(this.uiDao.getTitle())) {
            jta.append("标题: ");
            jta.append(ExchangeHtml.exchange(this.uiDao.getTitle()));
            jta.append("\r\n");
        }

        /*if (StringUtils.isNotBlank(this.uiDao.getUrl())) {
            jta.append("链接: ");
            jta.append(ExchangeHtml.exchange(this.uiDao.getUrl()));
            jta.append("\r\n");
        }*/

        if (StringUtils.isNotBlank(this.uiDao.getDescription())) {
            jta.append("描述: ");
            jta.append(ExchangeHtml.exchange(this.uiDao.getDescription()));
        } else if (StringUtils.isNotBlank(this.uiDao.getText())){
            jta.append("详情: ");
            jta.append(ExchangeHtml.exchange(this.uiDao.getText()));
        }

        this.setVisible(true);
    }
}
