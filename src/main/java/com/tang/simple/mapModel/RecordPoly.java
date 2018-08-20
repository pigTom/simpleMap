package com.tang.simple.mapModel;


import java.awt.*;
import java.awt.geom.Point2D;

/**
 * this class is the same form with shareFile
 */
public class RecordPoly implements RecordShape {
    private int recordNum;
    private int shapeType;
    private String name;
    private String pinyin;
    private Double minX;
    private Double minY;
    private Double maxX;
    private Double maxY;
    private Polygon[] polygons;
    private Point2D.Double centerPoint;

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Polygon[] getPolygons() {
        return polygons;
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
    public void translate(double scaleX, double scaleY, int[] parts, Point2D.Double[] points) {
        polygons = new Polygon[parts.length];
        for (int i = 0; i < parts.length -1; i++) {
            polygons[i] = new Polygon();
            for (int j = parts[i]; j < parts[i+1]; ++j) {
                polygons[i].addPoint((int)(scaleX * points[j].x),(int)(scaleY * ( points[j].y)));
            }
        }
        polygons[parts.length - 1] = new Polygon();
        for (int i = parts[parts.length-1]; i < points.length; ++i) {
            polygons[parts.length -1].addPoint((int)(scaleX * points[i].x),(int)(scaleY * (points[i].y)));
        }
        double centerX = (minX + maxX) * 0.5 * scaleX;
        double centerY = (maxY + minY) * 0.5 * scaleY;
        setCenterPoint(new Point2D.Double(centerX, centerY));
    }

    public void setCenterPoint(Point2D.Double centerPoint) {
        this.centerPoint = centerPoint;
    }
    public Point2D.Double getCenterPoint(){
        return centerPoint;
    }

    @Override
    public void paint(Graphics2D g, boolean ifFill) {
        for (Polygon polygon : this.getPolygons()) {
            if (ifFill) {
                g.fill(polygon);
            } else {
                g.draw(polygon);
            }
        }
    }
}
