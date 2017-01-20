package models.ui;

import com.qunar.piao.MainJob;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 14:38
 */
public class AppWindow extends JFrame {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JTextArea jta;
    private JScrollPane jscrollPane;
    private JPanel jPanel;
    private Container contentPane;
    private static JButton start;
    private static JButton stop;
    private MyListener myListener;

    private void init() {
        this.myListener = new MyListener();

        this.contentPane = this.getContentPane();
        this.contentPane.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(650, 360);

        this.jta = new JTextArea(10, 15);
        this.jta.setEditable(false);
        this.jta.setTabSize(4);
        this.jta.setLineWrap(true);// 激活自动换行功能
        this.jta.setWrapStyleWord(true);// 激活断行不断字功能
        this.jta.setText("欢迎使用监控系统\r\n");

        this.start = new JButton("开始");
        this.stop = new JButton("结束");
        this.start.setActionCommand("start");
        this.stop.setActionCommand("stop");
        this.start.addActionListener(myListener);
        this.stop.addActionListener(myListener);
        this.start.setEnabled(true);
        this.stop.setEnabled(false);

        this.jPanel = new JPanel();
        this.jPanel.add(this.start);
        this.jPanel.add(this.stop);

        this.jscrollPane = new JScrollPane(jta);
        this.contentPane.add(jscrollPane, BorderLayout.CENTER);
        this.contentPane.add(this.jPanel, BorderLayout.SOUTH);

        //显示位置
        int windowWidth = this.getWidth();                    //获得窗口宽
        int windowHeight = this.getHeight();                  //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit();             //定义工具包
        Dimension screenSize = kit.getScreenSize();            //获取屏幕的尺寸
        int screenWidth = screenSize.width;                    //获取屏幕的宽
        int screenHeight = screenSize.height;                  //获取屏幕的高
        this.setLocation(screenWidth/2 - windowWidth/2, screenHeight/2 - windowHeight/2);//设置窗口居中显示

        this.setVisible(false);
    }

    public AppWindow( ) {
        this.init();
        this.setTitle("监控系统");
    }

    public void addMessage(String msg) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        this.jta.append(df.format(now) + "  ----  " + msg + "\r\n");
        this.jta.setCaretPosition( this.jta.getText().length());
    }

    private class MyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ("start".equals(e.getActionCommand())) {
                MainJob.runTimer();

                start.setEnabled(false);
                stop.setEnabled(true);
            } else if ("stop".equals(e.getActionCommand())){
                MainJob.stopTimer();

                start.setEnabled(true);
                stop.setEnabled(false);
            }
        }
    }
}
