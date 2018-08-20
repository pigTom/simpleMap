package com.tang.simple.mapView;

import com.tang.simple.Utils.Fonts;
import com.tang.simple.Utils.MapTranslator;
import com.tang.simple.eventStudy.NormalMouseListener;
import com.tang.simple.eventStudy.ToolActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Dimension2D;
import java.net.URL;

public class MapFrame extends JFrame {
    public JPanel cards;
    public MapPanel panel;
    public NormalPanel normalPanel;
    private Thread thread;
    private JToolBar toolBar;
    private ToolActionListener toolActionListener;

    public MapFrame(MapPanel panel, ToolActionListener toolActionListener) {

        this.panel = panel;
        this.toolActionListener = toolActionListener;
        // menu bar
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.setFont(Fonts.font1);
        fileMenu(jMenuBar);
        railfallMenu(jMenuBar);
        otherMenu(jMenuBar);
        viewMenu(jMenuBar);
        this.setJMenuBar(jMenuBar);

        CardLayout cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        // map panel
        initToolBar(panel);
        cards.add(panel, "main panel");

        // normal panel
        normalPanel = new NormalPanel();
        NormalMouseListener listener = new NormalMouseListener(normalPanel);
        normalPanel.addMouseWheelListener(listener);
        normalPanel.addMouseMotionListener(listener);
        normalPanel.addMouseListener(listener);
        cards.add(normalPanel, "normal panel");

        cardLayout.first(cards);
        Container container = this.getContentPane();
        container.add(cards, "Center");

        URL url = MapFrame.class.getClassLoader().getResource("images/jb1.png");
        String path = "";
        if (url != null) {
            path = url.getPath();
            if (path.contains(".jar!")) {
                path = path.replaceAll("[/\\\\]\\w+\\.jar![/\\\\]", "/");
                path = path.replaceAll("^file:[/\\\\]", "");
            }
        }
        ImageIcon imageIcon = new ImageIcon(path);
        this.setIconImage(imageIcon.getImage());
        this.setTitle("降水可视化");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension2D d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((int) (d.getWidth() - MapTranslator.screenWidth) / 2,
                (int) (d.getHeight() - MapTranslator.screenHeight) / 2);
        this.setSize(MapTranslator.screenWidth, MapTranslator.screenHeight);
    }

    private void initToolBar(JPanel jPanel) {
        // java Tool bar
        toolBar = new JToolBar("工具栏");
        URL[] urls = new URL[6];
        urls[0] = MapPanel.class.getClassLoader().getResource("images//play1.png");
        urls[1] = MapPanel.class.getClassLoader().getResource("images/stop.png");
        urls[2] = MapPanel.class.getClassLoader().getResource("images/home2.png");
        urls[3] = MapPanel.class.getClassLoader().getResource("images/front.png");
        urls[4] = MapPanel.class.getClassLoader().getResource("images/next.png");
        urls[5] = MapPanel.class.getClassLoader().getResource("images/close2.png");
        ImageIcon imgs[] = new ImageIcon[6];
        JButton[] jButtons = new JButton[6];
        for (int i = 0; i < 6; i++) {
            if (urls[i] == null) {
                imgs[i] = new ImageIcon();
            } else {
                imgs[i] = new ImageIcon(urls[i]);
            }
            jButtons[i] = new JButton(imgs[i]);
            toolBar.add(jButtons[i]);
        }
        jButtons[0].setMnemonic(KeyEvent.VK_ENTER);
        jButtons[0].addActionListener((event) -> {
                    if (thread == null || !panel.countinue) {
                        panel.countinue = true;
                        thread = new Thread(panel);
                        System.out.print("start");
                        thread.start();
                    }
                }
        );

        jButtons[1].setMnemonic(KeyEvent.VK_S);
        jButtons[1].addActionListener((event) -> panel.countinue = false);
        jButtons[2].addActionListener((e) -> {
                    panel.waterReader = null;
                    panel.repaint();
                }
        );

        jButtons[3].addActionListener((e) -> {
                    if (panel.waterReader != null && panel.waterReader > 1) {
                        panel.waterReader -= 1;
                        panel.repaint();
                    }
                }
        );
        jButtons[4].addActionListener((e) -> {
            if (panel.waterReader != null && panel.waterReader < 11) {
                panel.waterReader += 1;
                panel.repaint();
            }

        });

        jButtons[5].addActionListener((e) ->
                    toolBar.setVisible(false)
        );

        toolBar.setFloatable(true);
//        toolBar.addSeparator();
        toolBar.setOrientation(SwingConstants.HORIZONTAL);

        jPanel.add(toolBar, BorderLayout.PAGE_START);
//        container.add(toolBar, "North");
    }

    private void fileMenu(JMenuBar jMenuBar) {
        JMenu jMenu = new JMenu("文件");
        jMenu.setMnemonic('F');
        JMenuItem openMenu = new JMenuItem("打开");
        JMenuItem save = new JMenuItem("保存");
        openMenu.setActionCommand("open");
        openMenu.addActionListener(toolActionListener);
        save.setActionCommand("save");
        save.addActionListener(toolActionListener);
        jMenu.add(openMenu);
        jMenu.add(save);
        jMenuBar.add(jMenu);
    }

    private void otherMenu(JMenuBar jMenuBar) {
        JMenu jMenu = new JMenu("其它地图");
        JMenuItem city = new JMenuItem("城市分布");
        JMenuItem river = new JMenuItem("河流分布");
        JMenuItem railway = new JMenuItem("铁路分布");
        JMenuItem highway = new JMenuItem("公路分布");
        JMenuItem province = new JMenuItem("省级分布");

        city.addActionListener(panel);
        city.setActionCommand("city");
        river.addActionListener(panel);
        river.setActionCommand("river");
        railway.addActionListener(panel);
        railway.setActionCommand("railway");
        highway.addActionListener(panel);
        highway.setActionCommand("highway");
        province.addActionListener(panel);
        province.setActionCommand("province");
        jMenu.add(city);
        jMenu.add(river);
        jMenu.add(railway);
        jMenu.add(highway);
        jMenu.add(province);
        jMenuBar.add(jMenu);
    }

    private void railfallMenu(JMenuBar jMenuBar) {
        JMenu waterMenu = new JMenu("降水图");
        JMenuItem openMenu = new JMenuItem("Open");
        openMenu.setActionCommand("open rainfall");
        openMenu.addActionListener(panel);
        JMenuItem item1 = new JMenuItem("1月降水图");
        item1.setActionCommand("water1");
        item1.addActionListener(panel);
        JMenuItem item2 = new JMenuItem("2月降水图");
        item2.setActionCommand("water2");
        item2.addActionListener(panel);
        JMenuItem item3 = new JMenuItem("3月降水图");
        item3.setActionCommand("water3");
        item3.addActionListener(panel);
        JMenuItem item4 = new JMenuItem("4月降水图");
        item4.setActionCommand("water4");
        item4.addActionListener(panel);
        JMenuItem item5 = new JMenuItem("5月降水图");
        item5.setActionCommand("water5");
        item5.addActionListener(panel);
        JMenuItem item6 = new JMenuItem("6月降水图");
        item6.setActionCommand("water6");
        item6.addActionListener(panel);
        JMenuItem item7 = new JMenuItem("7月降水图");
        item7.setActionCommand("water7");
        item7.addActionListener(panel);
        JMenuItem item8 = new JMenuItem("8月降水图");
        item8.setActionCommand("water8");
        item8.addActionListener(panel);
        JMenuItem item9 = new JMenuItem("9月降水图");
        item9.setActionCommand("water9");
        item9.addActionListener(panel);
        JMenuItem item10 = new JMenuItem("10月降水图");
        item10.setActionCommand("water10");
        item10.addActionListener(panel);
        JMenuItem item11 = new JMenuItem("11月降水图");
        item11.setActionCommand("water11");
        item11.addActionListener(panel);
        JMenuItem item12 = new JMenuItem("12月降水图");
        item12.setActionCommand("water12");
        item12.addActionListener(panel);
        waterMenu.add(item1);
        waterMenu.add(item2);
        waterMenu.add(item3);
        waterMenu.add(item4);
        waterMenu.add(item5);
        waterMenu.add(item6);
        waterMenu.add(item7);
        waterMenu.add(item8);
        waterMenu.add(item9);
        waterMenu.add(item10);
        waterMenu.add(item11);
        waterMenu.add(item12);
        jMenuBar.add(waterMenu);
    }

    private void viewMenu(JMenuBar menuBar) {
        JMenu menu = new JMenu("视图");
        JMenuItem toolBarItem = new JMenuItem("工具栏");
        toolBarItem.addActionListener((e) -> {
            if (toolBar != null)
                toolBar.setVisible(true);
        });

        JMenuItem mainMenu = new JMenuItem("主视图");
        mainMenu.addActionListener((e) ->{
                CardLayout c = (CardLayout) cards.getLayout();
                c.show(cards, "main panel");

        });
        JMenuItem opendMenu = new JMenuItem("最近打开");
        opendMenu.addActionListener((e) -> {
                CardLayout c = (CardLayout) cards.getLayout();
                c.show(cards, "normal panel");
            }
        );
        JMenuItem resetMap = new JMenuItem("重置地图");
        resetMap.setActionCommand("reset map");
        resetMap.addActionListener(toolActionListener);
        menu.add(toolBarItem);
        menu.add(mainMenu);
        menu.add(opendMenu);
        menu.add(resetMap);
        menuBar.add(menu);
    }
}
