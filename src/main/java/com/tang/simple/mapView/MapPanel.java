package com.tang.simple.mapView;

import com.tang.simple.Utils.Fonts;
import com.tang.simple.Utils.PathHelper;
import com.tang.simple.dataModel.BigArea;
import com.tang.simple.dataModel.ImageSave;
import com.tang.simple.dataModel.WaterData;
import com.tang.simple.mapModel.RecordList;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Resource
public class MapPanel extends BasePanel implements Runnable, ActionListener {

    private Color contentLineColor;
    private BigArea bigArea;
    private RecordList capital;
    public RecordList bodyPoly;
    private RecordList bodyLine;
    public Integer waterReader = 1;
    private BufferedImage southSeaImg;
    public  boolean countinue = true;
    public MapPanel() {

        borderColor = new Color(139, 90, 204, 150);

        contentLineColor = new Color(45, 237, 32, 250);
    }

    public void init() {
        bigArea = BigArea.newInstance();
        try {
            String img = "images/southSea.png";
            img = PathHelper.getPath(img);
            bigArea.setMapBounds(translate);
            southSeaImg = ImageIO.read(new File(img));
            bodyPoly = bigArea.getProvinceBoundPoly(translate.scaleX, translate.scaleY)   ;
            bodyLine = bodyPoly;
            capital = bigArea.getCapital(translate.scaleX, translate.scaleY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setColor(Graphics2D g) {
        int r = (int) (Math.random() * 55 + 200);
        int gg = (int) (Math.random() * 25 + 50);
        int b = (int) (Math.random() * 105 + 150);
        g.setColor(new Color(r, gg, b));
    }
    @Override
    public void run() {
        try {
            while (countinue) {
                for (int i = 1; i < 12; i++) {
                    waterReader = i;
                    this.repaint();
                    Thread.sleep(1000);
                    if (!countinue) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPanelListen(MouseAdapter adapter) {
        this.addMouseListener(adapter);
        this.addMouseWheelListener(adapter);
        this.addMouseMotionListener(adapter);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            switch (event.getActionCommand()) {
                case "railway":
                    bodyPoly = null;
                    bodyLine = bigArea.getRailway(translate.scaleX, translate.scaleY);
                    break;
                case "highway":
                    bodyPoly = null;
                    bodyLine = bigArea.getHighway(translate.scaleX, translate.scaleY);
                    break;
                case "province":
                    bodyPoly = bigArea.getProvinceBoundPoly(translate.scaleX, translate.scaleY);
                    bodyLine = bodyPoly;
                    break;
                case "city":
                    bodyPoly = null;
                    bodyLine = bigArea.getCityBound(translate.scaleX, translate.scaleY);
                    break;
                case "river":
                    bodyLine = bigArea.getRiverLevel1Line(translate.scaleX, translate.scaleY);
                    bodyLine.setColor(new Color(56, 88, 250));
                    bodyPoly = bigArea.getRiverLevel1Poly(translate.scaleX, translate.scaleY);
                    bodyPoly.setColor(new Color(20, 200, 255));
                    break;
                case "water1":
                    waterReader = 1;
                    break;
                case "water2":
                    waterReader = 2;
                    break;
                case "water3":
                    waterReader = 3;
                    break;
                case "water4":
                    waterReader = 4;
                    break;
                case "water5":
                    waterReader = 5;
                    break;
                case "water6":
                    waterReader = 6;
                    break;
                case "water7":
                    waterReader = 7;
                    break;
                case "water8":
                    waterReader = 8;
                    break;
                case "water9":
                    waterReader = 9;
                    break;
                case "water10":
                    waterReader = 10;
                    break;
                case "water11":
                    waterReader = 11;
                    break;
                case "water12":
                    break;
                case "save":
                    ImageSave.save(saveImage());
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.repaint();
    }

    @Override
    public void paint(Graphics gp) {
        super.paint(gp);
        Graphics2D g = (Graphics2D) gp;
        translate.translate(g, this);
        g.setColor(Color.green);
        if (waterReader != null) {
            new WaterData(waterReader, this).paint(g, true);
        }

        if (capital != null) {
            g.setColor(Color.red);
            capital.paint(g, false);
        }

        if (bodyLine != null) {
            g.setColor(contentLineColor);
            bodyLine.paint(g, false);
        }
        g.setColor(Color.green);
        drawSouthSea(g);

    }

    private void drawSouthSea(Graphics2D g) {
        g.setColor(Color.black);
        Point point = new Point(this.getWidth()-300, 635);
        translate.translatePoint(point, this.getWidth(),
                this.getHeight());
        g.drawRect(point.x, point.y, 220, 250);
        g.drawImage(southSeaImg, point.x + 1, point.y + 30, null);
        g.setFont(Fonts.font3);
//        g.drawString("中国南海图例", point.x + 50, point.y + 20);
    }
    public void resetMap() {
        repaint();
    }

}
