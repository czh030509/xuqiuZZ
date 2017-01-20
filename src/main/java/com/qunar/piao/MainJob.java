package com.qunar.piao;

import models.DefaultContant;
import models.ui.AppWindow;
import services.taoguba.TGBJob;
import services.weibo.XLJob;
import services.xueqiu.XueQiuJob;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 16:46
 */
public class MainJob {
    private static AppWindow appWindow = new AppWindow();
    private static boolean start = false;

    private static XueQiuJob xueQiuJob;
    private static TGBJob tgbJob;
    private static XLJob xlJob;

    public static void runTimer() {
        if (!start) {
            appWindow.addMessage("开始扫描");
            start = true;

            if (DefaultContant.isOpen_tgb()) {
                if (tgbJob == null) {
                    tgbJob = new TGBJob();
                }

                new Thread(tgbJob).start();
            }

            if (DefaultContant.isOpen_xueqiu()) {
                if (xueQiuJob == null) {
                    xueQiuJob = new XueQiuJob();
                }

                new Thread(xueQiuJob).start();
            }

            if (DefaultContant.isOpen_xl()) {
                if (xlJob == null) {
                    xlJob = new XLJob();
                }

                new Thread(xlJob).start();
            }
        }
    }

    public static void stopTimer() {
        if (start) {
            appWindow.addMessage("结束扫描");
            start = false;

            if (DefaultContant.isOpen_tgb()) {
                tgbJob.cancelTimer();
            }

            if (DefaultContant.isOpen_xueqiu()) {
                xueQiuJob.cancelTimer();
            }

            if (DefaultContant.isOpen_xl()) {
                xlJob.cancelTimer();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        appWindow.setVisible(true);

        ShowJob showJob = new ShowJob();
        new Thread(showJob).start();
    }

    public static AppWindow getAppWindow() {
        return appWindow;
    }

    public static boolean isStart() {
        return start;
    }
}
