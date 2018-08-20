package tang.simple.cordinate;

import com.tang.simple.Utils.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

/**
 * Created by Administrator on 2018/6/2.
 */
public class MapCordinate extends JPanel {
    boolean flag = false;
    public static Point.Double center = new Point2D.Double(300, 40);
    public static double scale = 20;

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        UIManager.put("MenuItem.font", Fonts.font1);
        UIManager.put("Menu.font", Fonts.font1);
        JFrame frame = new JFrame("tang");
        JMenuBar jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("hello");
        JMenuItem item = new JMenuItem("唐敦红");
        menu.add(item);
        jMenuBar.add(menu);
        frame.setJMenuBar(jMenuBar);
        MapCordinate mapCordinate = new MapCordinate();
        JToolBar toolBar = new JToolBar();
        toolBar.add(new Button("helloo"));
        mapCordinate.add(toolBar, BorderLayout.PAGE_START);

        mapCordinate.addMouseListener(new MouseAdapter() {
            PopupFactory factory = PopupFactory.getSharedInstance();
            Popup up = null;

            @Override
            public void mouseClicked(MouseEvent event) {
//                if (up != null) {
//                    up.hide();
//                }
                System.out.println(event.getPoint());
//                up = factory.getPopup(null, new Label("tang"), event.getXOnScreen(), event.getYOnScreen());
//                up.show();
                mapCordinate.flag = true;
                mapCordinate.repaint();
//                mapCordinate.flag = false;
            }
        });

        frame.add(mapCordinate);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1000, 1000);
    }

    public void paint(Graphics g1) {
        super.paint(g1);
        Graphics2D g = (Graphics2D) g1;
//        double[] flatMatrix = {1, 0, 0, 1, 0, 0};
//        AffineTransform transform = new AffineTransform(flatMatrix);
//        System.out.println("x: " + getWidth() + ", y: " + getHeight());
//        g.setTransform(transform);
        g.translate(400, 0);
        g.rotate(Math.PI / 4);
        g.translate(-400, 0);
//        g.translate((-scale * center.x ) , (-scale * center.y ));
//        g.translate(getWidth()/2, getHeight()/2 );
//        g.fillRect((int)(center.x * scale), (int)(center.y * scale), 100, 200);
        g.drawLine(0, 0, getWidth(), getHeight());
        g.setColor(Color.red);
        g.fillRect(400, 0, 100, 100);
    }

}
