package com.tang.simple.eventStudy;

import com.tang.simple.Utils.MapTranslator;
import com.tang.simple.mapModel.RecordList;
import com.tang.simple.mapModel.RecordPoly;
import com.tang.simple.mapModel.RecordShape;
import com.tang.simple.mapView.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class PanelMouseListener extends MouseAdapter{

    private MapPanel mapPanel;
    private MapTranslator translator;
    private PopupFactory factory = PopupFactory.getSharedInstance();
    private  JLabel label;
    public PanelMouseListener(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        translator = mapPanel.translate;
        label = new JLabel();
        label.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        label.setForeground(Color.red);
        label.setBackground(Color.BLACK);

    }
    private Popup popup;
    @Override
    public void mouseClicked(MouseEvent event) {
        Point point = event.getPoint();
        Point2D.Double p = translator.getRealPoint(point, mapPanel.getWidth(), mapPanel.getHeight());
        System.out.println(event.getPoint() + ": " + p);
        if (popup != null) {
            popup.hide();
        }
        if (mapPanel.bodyPoly != null) {
            showPopup(mapPanel.bodyPoly, event);
        }
        mapPanel.repaint();
    }


    private void showPopup(RecordList list, MouseEvent event) {
        Point point = event.getPoint();
        translator.translatePoint(point, mapPanel.getWidth(), mapPanel.getHeight());
        for (RecordShape mapPoly : list) {
            Polygon[] polygons = ((RecordPoly)mapPoly).getPolygons();
            for (Polygon polygon : polygons) {

                if (polygons[0] != polygon && polygons[0].contains(polygon.getBounds2D())) {
                    break;
                }
                if (polygon.contains(point)) {
                    label.setText(mapPoly.getName());
                    popup = factory.getPopup(mapPanel, label, event.getXOnScreen(), event.getYOnScreen());
                    popup.show();
                    mapPanel.setVisible(true);
                    return;
                }
            }
        }
    }

}
