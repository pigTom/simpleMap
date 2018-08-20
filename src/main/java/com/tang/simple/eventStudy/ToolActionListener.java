package com.tang.simple.eventStudy;

import com.tang.simple.Utils.ThreadHelper;
import com.tang.simple.dataModel.BigArea;
import com.tang.simple.dataModel.ImageSave;
import com.tang.simple.mapView.MapFrame;
import com.tang.simple.mapView.MapPanelFactory;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@Resource
public class ToolActionListener implements ActionListener {
    private Thread thread;
    MapFrame jFrame;
    ThreadHelper helper;
    CardLayout cardLayout;
    public void setjFrame(MapFrame jFrame) {
        this.jFrame = jFrame;
        cardLayout = (CardLayout)(jFrame.cards.getLayout());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("thread ");
        switch (event.getActionCommand()) {
            case "play":
                System.out.println("thread ");
                if (thread == null || !thread.isAlive()) {
                    thread = new Thread(MapPanelFactory.getInstance());
                    thread.start();
                }
                break;
            case "save":
                int count = jFrame.cards.getComponentCount();
                for (int i = 0; i < count; i++) {
                    Component c = jFrame.cards.getComponent(i);
                    if (c.isVisible()) {
                        ImageListener listener = (ImageListener) c;
                        ImageSave.save(listener.save());
                        break;
                    }
                }
                break;
            case "highway":
                break;
            case "respy":
                break;

            case "open":
                File path = ImageSave.open();
                if (path == null) {
                    return;
                }
                String pa = path.getAbsolutePath();
                if (!(pa.endsWith("txt") || pa.endsWith("shp"))) {
                    break;
                }

                helper = new ThreadHelper(pa, jFrame.normalPanel);
                // set bounds/
                jFrame.normalPanel.origin.setLocation(0, 0);
                BigArea.newInstance().setMapBounds(pa, jFrame.normalPanel.translate);
                Runnable a = new Runnable(){
                    @Override
                    public void run(){
                        new Thread(helper).start();
                        while (!helper.isOver) {
                            try {
                                Thread.sleep(101);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }

                        jFrame.normalPanel.path = path.getPath();
                        jFrame.normalPanel.setMap(helper.data);
                        cardLayout.show(jFrame.cards, "normal panel");
                        jFrame.repaint();
                    }
                };
                new Thread(a).start();
                break;
            case "reset map":
                int count1 = jFrame.cards.getComponentCount();
                for (int i = 0; i < count1; i++) {
                    Component c = jFrame.cards.getComponent(i);
                    if (c.isVisible()) {
                        ImageListener listener = (ImageListener) c;
                        listener.resetMap();
                        break;
                    }
                }

                break;
        }
    }
}
