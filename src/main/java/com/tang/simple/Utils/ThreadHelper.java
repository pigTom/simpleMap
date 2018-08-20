package com.tang.simple.Utils;

import com.tang.simple.dataModel.BigArea;
import com.tang.simple.dataModel.WaterData;
import com.tang.simple.mapModel.RecordList;
import com.tang.simple.mapView.BasePanel;

/**
 * Created by Administrator on 2018/5/27.
 */
public class ThreadHelper implements Runnable {
    private String path;
    public RecordList data;
    public boolean isOver = false;
    private FlashScreen flashScreen;
    private BasePanel panel;
    public ThreadHelper(String path, BasePanel panel) {
        this.path = path;
        this.panel = panel;
        flashScreen = new FlashScreen();
        flashScreen.setVisible(true);
    }
    public void run() {
        new Thread(flashScreen).start();
        try {
            if (path.endsWith(".txt")) {
                data = new WaterData(path, panel);
            } else {
                data = BigArea.newInstance().getRecordListByPath(path, panel.translate.scaleX, panel.translate.scaleY);

            }
            Thread.sleep(100);
            flashScreen.flag = false;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            isOver = true;
        }
    }

    public static void main(String[] args) {
        String str = "mapData\\CHN_adm0.shp";
        ThreadHelper helper = new ThreadHelper(str, null);
        new Thread(helper).start();
    }

}
