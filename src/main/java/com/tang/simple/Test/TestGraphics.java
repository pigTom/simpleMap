package com.tang.simple.Test;

import javax.swing.*;
import java.awt.*;

public class TestGraphics extends JPanel {
    @Override
    public void paint(Graphics g) {
        g.translate(-40, -40);
        g.drawRect(0, 0, 50, 50);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(100, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new TestGraphics());

        frame.setVisible(true);
    }
}
