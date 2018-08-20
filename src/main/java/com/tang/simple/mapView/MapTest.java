package com.tang.simple.mapView;

import com.tang.simple.dataModel.DataShpReader;

import javax.swing.*;
import java.awt.*;

public class MapTest extends JPanel{
    public static final String fileName = "mapData/CHN_adm0.shp";
    DataShpReader shpDataReader;
    Polygon[] polygons;
    public MapTest(){

    }
//    public void init() throws IOException{
//        FileInputStream inputStream = new FileInputStream(fileName);
//        MyDataInputStream in = new MyDataInputStream(inputStream);
//        shpDataReader = new DataShpReader(in);
//        shpDataReader.readSHPHeader();
//        shpDataReader.readRecord();
//        shpDataReader.polygon.init(1000, 1000);
//        polygons = shpDataReader.polygon.getPolygons();
//    }
//    @Override
//    public void paintComponent(Graphics graphics){
//        super.paintComponent(graphics);
//        Graphics2D g = (Graphics2D) graphics;
//        g.translate(shpDataReader.polygon.getMinX(), shpDataReader.polygon.getMinY());
////        setBackground(Color.yellow); //背景色为黄色
//        g.setXORMode(Color.yellow); //设置XOR绘图模式,颜色为红色
//
//        try {
//            drawPolygon(g);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws Exception{
//        MapTest test = new MapTest();
//
//        test.init();
//
//
////        for (com.tang.mapView.mapModel.RecordPoint p : test.shpDataReader.polygon.getPoints()){
////            if (p.getX() > 135 )
////                System.out.println(p);
////        }
//
//        JFrame jFrame = new JFrame();
//        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        jFrame.setSize(1000, 1000);
//        // new JPanel
//        jFrame.add(test);
//        //  new Thread(test).start();
//        jFrame.setVisible(true);
//    }
//
//    public void drawPolygon(Graphics2D g) throws IOException {
//
//
//        for (Polygon p : polygons){
//            g.drawPolygon(p);
//        }
//    }transient volatile
}
