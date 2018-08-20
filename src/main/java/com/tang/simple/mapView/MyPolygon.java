package com.tang.simple.mapView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MyPolygon implements Shape, java.io.Serializable{

    private Polygon polygon;
    private String name;

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyPolygon(Polygon polygon, String name) {
        this.polygon = polygon;
        this.name = name;
        init();

    }


    public MyPolygon() {
        init();
    }
    private void init() {
        this.addMouseListener(new MouseAdapter() {
            Popup popup;
            PopupFactory factory = PopupFactory.getSharedInstance();
            JLabel label = new JLabel();

            {
                label.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
                label.setForeground(Color.red);
                label.setBackground(Color.BLACK);

            }
            @Override
            public void mouseEntered(MouseEvent e) {
               label.setText(name);
                if (popup != null)
                    popup.hide();
                popup = factory.getPopup(null, label, e.getXOnScreen(), e.getYOnScreen());
                popup.show();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (popup != null)
                    popup.hide();

            }

        });
    }

    public void addMouseListener(MouseAdapter adapter) {

    }

    // shape method

    public Rectangle getBounds(){
        return polygon.getBounds();
    }
    public void reset() {
       polygon.reset();
    }


    public void invalidate() {
        polygon.invalidate();
    }


    public void translate(int deltaX, int deltaY) {
       polygon.translate(deltaX, deltaY);
    }


    public void addPoint(int x, int y) {
        polygon.addPoint(x, y);
    }


    public boolean contains(Point p) {
        return polygon.contains(p.x, p.y);
    }


    public boolean contains(int x, int y) {
        return polygon.contains((double) x, (double) y);
    }


    public Rectangle2D getBounds2D() {
        return getBounds();
    }

    public boolean contains(double x, double y) {
        return polygon.contains(x, y);
    }


    public boolean contains(Point2D p) {
        return polygon.contains(p.getX(), p.getY());
    }

    public boolean intersects(double x, double y, double w, double h) {
       return polygon.intersects(x, y, w, h);
    }

    public boolean intersects(Rectangle2D r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }


    public boolean contains(double x, double y, double w, double h) {
        return polygon.contains(x, y, w, h);
    }


    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }


    public PathIterator getPathIterator(AffineTransform at) {
        return polygon.getPathIterator(at);
    }
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return getPathIterator(at);
    }
}

