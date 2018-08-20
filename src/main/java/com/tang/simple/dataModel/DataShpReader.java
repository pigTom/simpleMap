package com.tang.simple.dataModel;

import com.tang.simple.mapModel.*;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.List;


public class DataShpReader extends DataReader {


    private Double scaleX;
    private Double scaleY;

    public DataShpReader(String fileName, Double scaleX, Double scaleY) {
        super(fileName);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    /**
     * @param offsetList shp文件中所有记录对应的offset(偏移量)列表
     * @return List<RecordPoly> offset列表对应的每个记录(原始的Polygon数据)
     * @throws IOException 抛出读文件时可以出现的异常
     */
    public RecordList readAllRecords(List<Integer> offsetList) throws IOException {
        RecordList records = new RecordList(offsetList.size());

        if (header == null) {
            readHeader();
        }
        records.setMinX((Double) header.get("minX"));
        records.setMinY((Double) header.get("minY"));
        records.setMaxX((Double) header.get("maxX"));
        records.setMaxY((Double) header.get("maxY"));

        int i = 0;
        while (i < offsetList.size()) {
            records.add(readRecord(offsetList.get(i)));
            i++;
        }
        return records;
    }

    /**
     * @param offset 读取该文件中的一个记录根据记录距离文件开头的偏移量，单位为字节
     * @return 返回该记录，此记录可能为Point, Polygon,Polyline 或其它。
     * @throws IOException 可能会抛出IO异常
     */
    public RecordShape readRecord(long offset) throws IOException {
        input.seek(offset);
        return readRecord();
    }

    /**
     * RecordShape 是所有流中读出的形状的基类
     * @return 返回RecordShape
     * @throws IOException 可能会抛出IO异常
     */
    public RecordShape readRecord() throws IOException {
        RecordShape shape;
        int shapeType = (Integer) header.get("shapeType");
        switch (shapeType) {
            case RecordShape.POINT:
                shape = readRecordPoint();
                break;
            case RecordShape.POLYLINE:
                shape = readRecordPolyline();
                break;
            case RecordShape.POLYGON:
                shape = readRecordPolygon();
                break;
            case RecordShape.MULTIPOINT:
                shape = readRecordMultiPoint();
                break;
            default:
                throw new IOException("不能处理 shapeType = " + shapeType + " 这种类型的数据");
        }
        return shape;
    }

    private RecordShape readRecordPolygon() throws IOException {
        RecordPoly polygon = new RecordPolygon();
        readRecord(polygon);
        return polygon;
    }

    private RecordShape readRecordPolyline() throws IOException {
        RecordPoly poly = new RecordPolyline();
        readRecord(poly);
        return poly;
    }

    private RecordShape readRecordPoint() throws IOException {
        RecordPoint point = new RecordPoint();
        // read record head 8 bytes
        point.setRecordNum(input.readLittleEndianInt());
        input.skipBytes(4);

        // read record content
        point.setShapeType(input.readLittleEndianInt());
        point.x = input.readLittleEndianDouble();
        point.y = input.readLittleEndianDouble();
        point.translate(scaleX, scaleY, null, null);
        return point;
    }

    private RecordShape readRecordMultiPoint() throws IOException {
        RecordMultiPoint multiPoint = new RecordMultiPoint();
        multiPoint.setRecordNum(input.readLittleEndianInt());
        input.skipBytes(4);
        // read record content
        multiPoint.setMinX(input.readLittleEndianDouble());
        multiPoint.setMinY(input.readLittleEndianDouble());
        multiPoint.setMaxX(input.readLittleEndianDouble());
        multiPoint.setMaxY(input.readLittleEndianDouble());
        int pointNum = input.readLittleEndianInt();
        multiPoint.setShapeType(input.readLittleEndianInt());
        Point2D.Double[] points = readPoints(pointNum);
        multiPoint.translate(scaleX , scaleY, null, points);
        return  multiPoint;
    }
    private void readRecord(RecordPoly poly) throws IOException {
        // read record header
        int recordNum = input.readBigEndianInt();
        poly.setRecordNum(recordNum);
        // unused content length 4 byte
        input.skipBytes(4);

        // read record content
        // shape type
        // shapeType Integer type 4 bytes
        poly.setShapeType(input.readLittleEndianInt());
        poly.setMinX(input.readLittleEndianDouble());
        poly.setMinY(input.readLittleEndianDouble());
        poly.setMaxX(input.readLittleEndianDouble());
        poly.setMaxY(input.readLittleEndianDouble());
        int partNum = input.readLittleEndianInt();
        int pointNum = input.readLittleEndianInt();
        int[] part = readParts(partNum);
        Point2D.Double[] points = readPoints(pointNum);
        poly.translate(scaleX, scaleY, part, points);
    }

    /**
     *
     * @param size part number Part的个数
     * @return 返回Part数组
     * @throws IOException 可能会抛出IO异常
     */
    private int[] readParts(int size) throws IOException {
        int[] ins = new int[size];
        for (int i = 0; i < size; i++) {
            ins[i] = input.readLittleEndianInt();
        }
        return ins;
    }

    /**
     *  根据point number读出Point数组
     * @param size Point点的个数
     * @return 返回Point数组
     * @throws IOException 可能会抛出IO异常
     */
    private Point2D.Double[] readPoints(int size) throws IOException {
        Point2D.Double[] points = new Point2D.Double[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point2D.Double();
            points[i].x = input.readLittleEndianDouble();
            points[i].y = input.readLittleEndianDouble();
        }

        return points;
    }

    /**
     * 释放该对象中的流，以及其它资源
     */
    public void close() throws IOException {
        super.close();
    }
}
