package com.tang.simple.Utils;

/**
 * Created by Administrator on 2018/5/27.
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class FlashScreen extends JWindow implements Runnable{

    public boolean flag = true;
    private Flash f;
    public FlashScreen()
    {
        f = new Flash();
        Thread t = new Thread(f);
        t.start();
        this.add(f);

        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.setLocation(width/2-400, height/2-200);
        this.setVisible(true);
        this.setSize(650, 400);
        this.setFocusable(true);
        this.setFocusableWindowState(true);

    }

    public static void main(String[] args) {
        new FlashScreen();
    }
    @Override
    public void run() {
        while(flag)
        {
            try {
                f.flag = this.flag;
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
       f.flag = false;
        this.dispose();
    }

}

class Flash extends JPanel implements Runnable
{
    boolean flag = true;
    Image im = null;
    int width = 0;
    //generate changeable color
    int red = 0;
    int green = 0;
    int blue = 0;
    int controler;
    public Flash()
    {
        controler = 0;
        try {

            im = ImageIO.read(new File(PathHelper.getPath("images/picki.jpeg")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(flag)
        {

            width += 24;

            controler++;
            red = (int)(Math.random()*255);
            green = (int)(Math.random()*255);
            blue = (int)(Math.random()*255);

            this.repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        flag = false;
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        //draw image
        g.drawImage(im, 0, 0, 650, 400, this);

        drawBar(g);
        drawFlaB(g);
//        switch(controler%3)
//        {
//            case 0:
//                break;
//            case 1:
//                drawFlash1(g);
//                break;
//            case 2:
//                drawFlash2(g);
//                break;
//        }

    }
    void drawBar(Graphics g)
    {
        //white bar
        g.setColor(new Color(250, 250, 250, 255));
        g.fillRect(0, 300, 650, 100);
        // the green rectangle
        g.setColor(new Color(0, 200, 0, 150));
        g.drawRect(0, 320, 645, 60);
        g.setColor(Color.red);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("请稍等......", 160, 360);
    }
    void drawFlaB(Graphics g)
    {
        //the blue bar
        g.setColor(new Color(0, 0, 250, 150));
        g.fillRect(0, 325, width, 50);
    }
    void drawFlash1(Graphics g)
    {
        g.setColor(new Color(red, green, blue, 100));
        g.fillRect(width, 325, 20, 50);
    }
    void drawFlash2(Graphics g)
    {
        g.setColor(new Color(red, green, blue, 100));
        g.fillRect(width, 315, 20, 70);
    }

}

