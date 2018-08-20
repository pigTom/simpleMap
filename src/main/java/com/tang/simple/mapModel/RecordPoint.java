package com.tang.simple.mapModel;

import java.awt.*;
import java.awt.geom.Point2D;

public class RecordPoint extends Point2D.Double implements RecordShape {
	private static final long serialVersionUID = 1L;
	private String name;
    private String pinyin;
    private int recordNum;
    private int shapeType;
    @Override
    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    @Override
    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
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

    public int getShapeType() {
        return shapeType;
    }

    @Override
    public void translate(double scaleX, double scaleY, int[] parts, Point2D.Double[] points) {
        this.x = scaleX * x;
        this.y = scaleY * y;
    }

    @Override
    public void paint(Graphics2D g, boolean ifFill) {
        if (ifFill) {
            g.fillOval((int)(x-4), (int)(y-4), 8, 8);
            g.fillOval((int)x, (int)y, 2, 2);
        }
        else{
            g.drawOval((int)(x-4), (int)(y-4), 8, 8);
            g.drawOval((int)(x-1), (int)(y-1), 2, 2);
        }
    }
}
