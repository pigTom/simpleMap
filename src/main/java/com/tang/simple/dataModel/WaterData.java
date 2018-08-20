package com.tang.simple.dataModel;

import com.tang.simple.Utils.Fonts;
import com.tang.simple.Utils.MapTranslator;
import com.tang.simple.Utils.PathHelper;
import com.tang.simple.mapModel.RecordList;
import com.tang.simple.mapView.BasePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/15.
 */
public class WaterData extends RecordList{
    private BufferedReader reader;
    private Map<String, Double> header;
    private Color color1 = new Color(255, 0, 255, 200);
    private Color color2 = new Color(0, 0, 255, 200);
    private Color color3 = new Color(0, 100, 255, 200);
    private Color color4 = new Color(0, 150, 255, 200);
    private Color color5 = new Color(0, 255, 255, 200);
    private Color color6 = new Color(100, 255, 100, 200);
    private Color color7 = new Color(180, 255, 100, 200);
    private Color color8 = new Color(255, 255, 180, 200);
    private Color color9 = new Color(255, 255, 255, 200);
    private Integer month;
    private String path;
    private MapTranslator translator;
    private BasePanel jComponent;
    public WaterData(Integer month, BasePanel jComponent) {
        String basePath = "SURF_CLI_CHN_PRE_MON_GRID_0.5-2016";
        this.month = month;
        String path;
        if (month < 10) {
            path = basePath + "0"+ month + ".txt";
        } else {
            path = basePath + month + ".txt";
        }
        init(path, jComponent);
    }

    private void init(String path, BasePanel jComponent) {
        this.path = PathHelper.getPath(path);
        if (jComponent != null) {
            this.jComponent = jComponent;
            this.translator = jComponent.translate;
        }
    }
    public WaterData(String path, BasePanel jComponent) {
        init(path, jComponent);
    }
    public Map readHeader() {
        try {
            try {

                reader = new BufferedReader(new FileReader(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            header = new HashMap<>(6);
            for (int i = 0; i < 6; i++) {
                String line = reader.readLine();
                String[] lines = line.split("\\s");
                header.put(lines[0], Double.valueOf(lines[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return header;
    }

    @Override
    public void paint(Graphics2D g, boolean isFill) {

        readHeader();
        paintTitle(g);
        paintLegend(g);
        try {
            String line = reader.readLine();
            Double cellsize = header.get("cellsize");
            Double xllcorner = header.get("xllcorner"); // 经度的起始度
            Double yllcorner = header.get("yllcorner"); // 纬度的起始度
            double y = yllcorner + header.get("nrows") * cellsize; // 实际的纬度
            while (line != null) {
                String[] lines = line.trim().split("\\s+");
                double x = xllcorner; // 实际的经度
                for (int i = 0; i < lines.length; i++) {
                    if (lines[i] != null && !lines[i].equals("")) {
                        Double value = Double.parseDouble(lines[i]);
                        if (value == header.get("nodata_value").intValue()) {
//                            g.setColor(g.getBackground());
                        } else {
                            if (value > 800) {
                                g.setColor(color1);
                            } else if (value > 600) {
                                g.setColor(color2);
                            } else if (value > 400) {
                                g.setColor(color3);
                            } else if (value > 200) {
                                g.setColor(color4);
                            } else if (value > 100) {
                                g.setColor(color5);
                            } else if (value > 50) {
                                g.setColor(color6);
                            } else if (value > 10) {
                                g.setColor(color7);
                            } else if (value > 0) {
                                g.setColor(color8);
                            } else {
                                g.setColor(color9);
                            }
                            g.fillRect((int) (x * translator.scaleX), (int) (translator.scaleY * (y)),
                                    (int) (translator.scaleX / 1.5), (int) (-translator.scaleY/1.5));
                        }

                    }
                    x = x + cellsize;
                }
                y = y - cellsize;
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void paintTitle(Graphics2D g) {
        if (month == null) {
            return;
        }
        g.setFont(Fonts.font3);
        Point point = new Point(jComponent.getWidth() / 2 - 100, 100);
        translator.translatePoint(point, jComponent.getWidth(), jComponent.getHeight());
        g.setColor(Color.black);
        g.drawString("全国" + month + "月降水图", point.x, point.y);
    }
    private void paintLegend(Graphics2D g) {
        g.setFont(Fonts.font1);
        Point point = new Point(30, 635);
        // 外框
        g.setColor(Color.black);
        translator.translatePoint(point, jComponent.getWidth(), jComponent.getHeight());
        System.out.println("legend: [x=" + point.x / translator.scaleX + ", y=" + point.y / translator.scaleY + "]");
        g.drawRect(point.x, point.y, 240, 245);
        g.setColor(Color.black);
        g.drawString("降水图例(毫米)", point.x + 65, point.y + 20);
        // 1-1.5 毫米
        g.drawString(">800", point.x + 140, point.y + 55);
        g.setColor(color1);
        g.fillRect(point.x + 30, point.y + 40, 100, 20);

        // 1.6-6.9
        g.setColor(Color.black);
        g.drawString("600~800", point.x + 140, point.y + 75);
        g.setColor(color2);
        g.fillRect(point.x + 30, point.y + 60, 100, 20);

        // 7.0-14.9
        g.setColor(Color.black);
        g.drawString("400~600", point.x + 140, point.y + 95);
        g.setColor(color3);
        g.fillRect(point.x + 30, point.y + 80, 100, 20);

        // 15-39.9
        g.setColor(Color.black);
        g.drawString("200~400", point.x + 140, point.y + 115);
        g.setColor(color4);
        g.fillRect(point.x + 30, point.y + 100, 100, 20);

        // >=40
        g.setColor(Color.black);
        g.drawString("100~200", point.x + 140, point.y + 135);
        g.setColor(color5);
        g.fillRect(point.x + 30, point.y + 120, 100, 20);

        g.setColor(Color.black);
        g.drawString("50~100", point.x + 140, point.y + 155);
        g.setColor(color6);
        g.fillRect(point.x + 30, point.y + 140, 100, 20);

        g.setColor(Color.black);
        g.drawString("10~50", point.x + 140, point.y + 175);
        g.setColor(color7);
        g.fillRect(point.x + 30, point.y + 160, 100, 20);

        g.setColor(Color.black);
        g.drawString("0~10", point.x + 140, point.y + 195);
        g.setColor(color8);
        g.fillRect(point.x + 30, point.y + 180, 100, 20);

        g.setColor(Color.black);
        g.drawString("缺资料", point.x + 140, point.y + 215);
        g.setColor(color9);
        g.fillRect(point.x + 30, point.y + 200, 100, 20);

    }
}
