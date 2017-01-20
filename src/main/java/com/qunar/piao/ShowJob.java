package com.qunar.piao;

import models.UiDao;
import models.ui.TipsWindow;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/1 19:46
 */
public class ShowJob implements Runnable{
    private static BlockingQueue<UiDao> myBlockingQueue = new ArrayBlockingQueue<UiDao>(50);

    public static BlockingQueue<UiDao> getMyBlockingQueue() {
        return myBlockingQueue;
    }

    public void run() {
        while (true) {
            try {
                UiDao uiDao = myBlockingQueue.take();
                showTip(uiDao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void showTip(UiDao uiDao) {
        TipsWindow tipsWindow = new TipsWindow(uiDao);
        tipsWindow.showMessage();
    }
}
