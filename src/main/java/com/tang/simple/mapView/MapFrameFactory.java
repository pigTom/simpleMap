package com.tang.simple.mapView;

import com.tang.simple.eventStudy.ToolActionListener;

/**
 * Created by Administrator on 2018/5/25.
 */
public class MapFrameFactory {
    public static MapFrame getInstance(MapPanel panel, ToolActionListener toolActionListener) {
        return A.getPanel(panel, toolActionListener);
    }
    static class A {
        private static MapFrame frame;
        public static MapFrame getPanel(MapPanel panel, ToolActionListener toolActionListener){
            frame = new MapFrame(panel, toolActionListener);
            return frame;
        }
    }
}
