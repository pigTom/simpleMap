package tang.simple;

import com.tang.simple.Test.TestAfflineTransfor;
import com.tang.simple.eventStudy.PanelMouseListener;
import com.tang.simple.eventStudy.ToolActionListener;
import com.tang.simple.mapView.MapFrame;
import com.tang.simple.mapView.MapPanel;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

public class TestFrame {

    public static void testFrame(){
        TestAfflineTransfor test = new TestAfflineTransfor();
        MapPanel panel = new MapPanel();
        test.add(panel);
        test.setVisible(true);
    }

    @Test
    public void testGraphicsDrawstring(){
        JPanel panel = new MyPanel2();
        TestAfflineTransfor test = new TestAfflineTransfor();
        test.add(panel);
        test.setVisible(true);
    }

    public static void main(String[] args) {
        // 建立panel
        MapPanel panel = new MapPanel();
        ToolActionListener toolActionListener = new ToolActionListener();

        // 建立frame
        MapFrame frame = new MapFrame(panel, toolActionListener);
        // 建立监听器
        PanelMouseListener listener = new PanelMouseListener(panel);
        frame.add(panel);
        frame.setVisible(true);
//        new Thread(panel).start();

//        panel.initScale();
        panel.addPanelListen(listener);
    }
    class MyPanel2 extends JPanel{
        Polygon polygon;

        public MyPanel2() {

            polygon = new Polygon();
            polygon.addPoint(530, 530);
            polygon.addPoint(530, 580);
            polygon.addPoint(560, 580);
            polygon.addPoint(560, 530);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Point point = e.getPoint();
                    point.translate(100, 100);
                    if (polygon.contains(point)) {
                        System.out.println("---------------------ok----------------");
                    }

                    System.out.println(point);
                }
            });
        }
        @Override
        public void paint(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics;

            double[] flatMatrix = {1, 0, 0, 1, 800 / 2.0, 800 / 2.0};
            AffineTransform transform = new AffineTransform(flatMatrix);
            g.setTransform(transform);

            g.translate(-500, -500);
            // g.transform(new AffineTransform(1, 0, 0, -1, 0, 800));


            g.fill(polygon);

        }
    }

}
