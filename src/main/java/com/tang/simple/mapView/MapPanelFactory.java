package com.tang.simple.mapView;

/**
 * Created by Administrator on 2018/5/24.
 */
public class MapPanelFactory {

    public static MapPanel getInstance() {
        return A.getPanel();
    }
    static class A {
        private static MapPanel panel;
        public static MapPanel getPanel(){
            panel = new MapPanel();
            return panel;
        }
    }
}
