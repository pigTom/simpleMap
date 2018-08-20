package com.tang.simple.mapView;

import com.tang.simple.Utils.MapTranslator;
import com.tang.simple.Utils.ThreadHelper;
import com.tang.simple.dataModel.BigArea;
import com.tang.simple.eventStudy.ImageListener;
import com.tang.simple.mapModel.RecordList;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;


public class BasePanel extends JPanel implements ImageListener {
    public String path;
    protected RecordList map;
    public MapTranslator translate;
    public Point2D.Double origin = new Point2D.Double(0, 0);
    protected Color borderColor = new Color(139, 90, 204, 150);
    // 火山色
    private Color contentColor = new Color(240, 200, 160);
    public BasePanel () {
        translate = new MapTranslator();
    }
    public BufferedImage saveImage() {
        BufferedImage paintImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics g = paintImage.getGraphics();
        paint(g);
        g.dispose();
        return paintImage;
    }
    @Override
    public void resetMap() {
        this.origin.setLocation(0, 0);
        BigArea.newInstance().setMapBounds(path, this.translate);
        ThreadHelper helper = new ThreadHelper(path, this);
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
                setMap(helper.data);
                repaint();
            }
        };
        new Thread(a).start();

    }
    @Override
    public BufferedImage save() {
        return saveImage();
    }
    public void setMap(RecordList map) {
        this.map = map;
    }
}

