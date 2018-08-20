package com.tang.simple.mapModel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordList extends ArrayList<RecordShape> {
	static final long serialVersionUID = 1L; 
    public RecordList(){
        super();
    }

    public RecordList(int initialCapacity) {
        super(initialCapacity);
    }

    private Double minX;
    private Double minY;
    private Double maxX;
    private Double maxY;
    private Integer shapeType;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getShapeType() {
        return shapeType;
    }

    public void setShapeType(Integer shapeType) {
        this.shapeType = shapeType;
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

    public void setNames(List<String> names) throws IOException{
        if (names.size() != this.size()) {
            throw new IOException("record in 'shp' file incompatible with 'dbf' file");
        }
        for (int i = 0; i< this.size(); i++) {
            RecordShape mapPoly = this.get(i);
            mapPoly.setName(names.get(i));
        }
    }

    public void paint(Graphics2D g, boolean isFill) {
        if (color != null) {
            g.setColor(color);
        }
        for (RecordShape shape : this) {
            shape.paint(g, isFill);
        }
    }

}
