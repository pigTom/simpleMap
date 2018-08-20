package com.tang.simple.mapView;

import java.awt.*;


/**
 * Created by Administrator on 2018/5/25.
 */
public class NormalPanel extends BasePanel{
    @Override
    public void paint(Graphics gp) {
        super.paint(gp);
        Graphics2D g = (Graphics2D) gp;
        translate.translate(g, this);
        g.translate(origin.x, origin.y);
        if (map != null) {
            g.setColor(borderColor);
            map.paint(g, false);
        }

    }
}
