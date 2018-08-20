package tang.simple.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/25.
 */
public class TestClip {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel1 jPanel1 = new JPanel1();
        frame.add(jPanel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);

        jPanel1.updatePaint();
        try {
            jPanel1.save();
          //  jPanel1.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
class JPanel1 extends JPanel {
    private BufferedImage paintImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_BGR);

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.blue);
        g.fillRect(20, 20, 300, 300);
        g.drawString("Hello world", 40, 340);
//        graphics.drawImage(paintImage, 0, 0, null);
    }
//    @Override
//    public void paint(Graphics graphics) {
//        graphics.setColor(Color.red);
//        graphics.fillRect(0, 0, 100, 100);
//        Rectangle rectangle = new Rectangle(0, 0, 500, 500);
//        graphics.setClip(rectangle);
//        graphics.copyArea(50, 50, 100, 100, 30, 30);
//    }
    // draw painting
    public void updatePaint(){
        Graphics g = paintImage.createGraphics();

        this.paint(g);
        // draw on paintImage using Graphics
//        g.setColor(Color.blue);
//        g.fillRect(20, 20, 300, 300);
//        g.drawString("Hello world", 40, 340);
        g.dispose();
        // repaint panel with new modified paint
        repaint();
    }

    public void save() throws IOException {
        ImageIO.write(paintImage, "PNG", new File("filename2.png"));
    }

    public void load() throws IOException {
        paintImage = ImageIO.read(new File("filename.png"));
        // update panel with new paint image
        repaint();
    }
}