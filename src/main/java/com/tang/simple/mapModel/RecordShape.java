package com.tang.simple.mapModel;

import java.awt.*;
import java.awt.geom.Point2D;

public interface RecordShape {
    int NULL = 0;
    int POINT = 1;
    int POLYLINE = 3;
    int POLYGON = 5;
    int MULTIPOINT = 8;
    int POINTZ = 11;
    int POLYLINEZ = 13;
    int POLYGONZ = 15;
    int MULTIPOINTZ = 18;
    int POINTM = 21;
    int POLYLINEM = 23;
    int POLYGONM = 25;
    int MULTIPOINTM = 28;
    int MULTIPATCH = 31;
    /** shape type是形状的类型，更多信息请参考<p>shapefile</p>
     * 0 Null Shape
     * 1 Point 代表单点
     * 3 Polyline 代表多线段
     * 5 Polygon 代表多边形
     * 8 MultiPoint 代表多点
     * 11 PointZ
     * 13 PolylineZ
     * 15 PolygonZ
     * 18 MultiPointZ
     * 21 PointM
     * 23 PolylineM
     * 25 PolygonM
     * 28 MultiPointM
     * 31 MultiPatch
     * @return 返回形状的类型
     */
    int getShapeType();

    void setShapeType(int shapeType);
    /**
     *  将地图数据点转换为屏幕像素点
     * @param scaleX 地图数据经度相对于屏幕的缩放比例
     * @param scaleY 地图数据纬度相对于屏幕的绽放比例
     */
    void translate(double scaleX, double scaleY,  int[] parts, Point2D.Double[] points);

    void setName(String name);

    String getName();

    void setPinyin(String pinyin);

    String getPinyin();

    void paint(Graphics2D g, boolean ifFill);

    void setRecordNum(int recordNum);

    int getRecordNum();
}
