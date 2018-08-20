package com.tang.simple.mapModel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class RecordMultiPoint implements RecordShape {
    private String name;
    private String pinyin;
    private Point2D.Double[] points;
    private Double minX;
    private Double minY;
    private Double maxX;
    private Double maxY;
    private int recordNum;
    private int shapeType;

    public Double getMinX() {
        return minX;
    }

    public void setMinX(Double minX) {
        this.minX = minX;
    }

    public Double getMinY() {
        return minY;
    }

    public void setMinY(Double minY) {
        this.minY = minY;
    }

    public Double getMaxX() {
        return maxX;
    }

    public void setMaxX(Double maxX) {
        this.maxX = maxX;
    }

    public Double getMaxY() {
        return maxY;
    }

    public void setMaxY(Double maxY) {
        this.maxY = maxY;
    }

    @Override
    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    public int getRecordNum() {
        return recordNum;
    }

    @Override
    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPinyin() {
        return pinyin;
    }

    @Override
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int getShapeType() {
        return shapeType;
    }

    @Override
    public void translate(double scaleX, double scaleY, int[] parts, Point2D.Double[] points) {
        this.points = points;
        for (Point2D.Double point : this.points) {
            point.x *= scaleX;
            point.y *= scaleY;
        }
    }

    @Override
    public void paint(Graphics2D g, boolean ifFill) {
        for (Point2D.Double point : this.points) {
            if (ifFill) {
                g.fillOval((int)(point.x-2), (int)(point.y-2), 4, 4);
            }
            else{
                g.drawOval((int)(point.x-2), (int)(point.y-2), 4, 4);
            }
        }
    }

    @Override
    public String toString() {
        return "RecordMultiPoint{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", points=" + Arrays.toString(points) +
                ", minX=" + minX +
                ", minY=" + minY +
                ", maxX=" + maxX +
                ", maxY=" + maxY +
                ", recordNum=" + recordNum +
                ", shapeType=" + shapeType +
                '}';
    }
}
