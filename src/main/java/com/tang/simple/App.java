package com.tang.simple;

import com.tang.simple.Utils.Fonts;
import com.tang.simple.eventStudy.PanelMouseListener;
import com.tang.simple.eventStudy.ToolActionListener;
import com.tang.simple.mapView.MapFrame;
import com.tang.simple.mapView.MapPanel;
import com.tang.simple.mapView.MapPanelFactory;

import javax.swing.*;

/**
 * Created by Administrator on 2018/5/25.
 */
public class App {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        UIManager.put("MenuItem.font", Fonts.font1);
        UIManager.put("Menu.font", Fonts.font1);


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });


    }

    public static void showGUI() {
        // 建立panel
        MapPanel panel = MapPanelFactory.getInstance();
        // 建立frame
        ToolActionListener toolActionListener = new ToolActionListener();
        MapFrame frame = new MapFrame(panel, toolActionListener);
        panel.init();
        toolActionListener.setjFrame(frame);
        // 建立监听器
        PanelMouseListener listener = new PanelMouseListener(panel);
//        frame.add(panel);
        frame.setVisible(true);
        panel.addPanelListen(listener);

    }
}
