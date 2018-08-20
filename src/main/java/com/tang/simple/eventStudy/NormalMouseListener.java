package com.tang.simple.eventStudy;

import com.tang.simple.Utils.MapTranslator;
import com.tang.simple.Utils.ThreadHelper;
import com.tang.simple.mapView.NormalPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

public class NormalMouseListener extends MouseAdapter {

    private NormalPanel mapPanel;
    private MapTranslator translator;
    public NormalMouseListener(NormalPanel mapPanel) {
        this.mapPanel = mapPanel;
        translator = mapPanel.translate;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //  在这里实现地图的缩放
        if (mapPanel.isVisible() && e.isControlDown()) {
            int rotation = e.getWheelRotation();
            switch (rotation) {
                case -1:
                    if (translator.scaleX < 1000) {
                        translator.scaleX *= 1.2;
                        translator.scaleY *= 1.2;
                    }
                    break;

                case 1:
                    if (translator.scaleX > 0.25) {
                        translator.scaleX /= 1.2;
                        translator.scaleY /= 1.2;
                    }
            }

            ThreadHelper helper = new ThreadHelper(mapPanel.path, mapPanel);
            Runnable a = new Runnable(){
                @Override
                public void run(){
                    new Thread(helper).start();
                    while (!helper.isOver) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    mapPanel.setMap(helper.data);
                    mapPanel.repaint();
                }
            };
            new Thread(a).start();

        }
    }


    private Integer startX = 0;
    private Integer startY = 0;

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    /**
     * 如果按下ctrl键并且鼠标拖动，就能拖动画布
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.isControlDown()) {
            int endX = e.getX();
            int endY = e.getY();
            Point2D.Double p = mapPanel.origin;
            mapPanel.origin.setLocation(p.x + (endX - startX), p.y + (endY - startY));
            mapPanel.repaint();
            startX = endX;
            startY = endY;
        }
    }

}
