package com.tang.simple.Test;

import com.tang.simple.mapView.MapPanel;

import javax.swing.*;

public class TestAfflineTransfor extends JFrame{

//        JPanel panel;
    public TestAfflineTransfor(){
//        this.panel = panel;
        this.setSize(800, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//        this.add(panel);
     //   this.setVisible(true);

    }
    public static void main(String[] args){

        MapPanel panel = new MapPanel();
     //  panel.setBackground(new Color(150, 54, 210));

        TestAfflineTransfor t = new TestAfflineTransfor();
        t.add(panel);


        t.setVisible(true);
//        panel.initScale();
   //    new Thread(panel).start();
    }


}
