package com.tang.simple.dataModel;

import com.tang.simple.mapModel.RecordList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.tang.simple.Utils.MapTranslator.screenHeight;
import static com.tang.simple.Utils.MapTranslator.screenWidth;

public class DataHelper {
    private DataDbfReader dbfReader;
    private DataShpReader shpReader;
    private DataShxReader shxReader;
    private String dbfFile;
    private String shpFile;
    private String shxFile;
    private Double scaleX;
    private Double scaleY;

    public DataHelper(String basePath, Double scaleX, Double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        if (basePath.endsWith(".shp") || basePath.endsWith(".shx") || basePath.endsWith(".dbf")) {
            basePath = basePath.substring(0, basePath.length() - 4);
        }
        shpFile = basePath + ".shp";
        shxFile = basePath + ".shx";
        dbfFile = basePath + ".dbf";
    }

    /**
     * 先读取shx文件得到头及index，再从shp文件中读取数据
     *
     * @return 返回从shp文件中读取的所有原始的<code>RecordPoly</code>
     */
    public RecordList getRecords() throws IOException{
        shxReader = new DataShxReader(shxFile);
        List<Integer> offsetList = shxReader.readOffsetList();

        if (scaleX == null || scaleY == null) {
            Map<String, Object> header = shxReader.getHeader();
            double maxX = (double)header.get("maxX");
            double minX = (double)header.get("minX");
            double maxY = (double)header.get("maxY");
            double minY = (double)header.get("minY");
            double maxDistanceX = maxX - minX;
            double maxDistanceY = maxY - minY;
            scaleX = screenWidth / maxDistanceX /1.2;
            scaleY = -screenHeight / maxDistanceY /1.2;
        }
        shpReader = new DataShpReader(shpFile, scaleX, scaleY);
        shpReader.setHeader(shxReader.getHeader());
        return shpReader.readAllRecords(offsetList);
    }


    /**
     * 根据字段的名字获取字段的值
     * @param fieldName 字段名称
     * @return 返回一个以字段名为键，以字段值为值的map
     * @throws IOException
     */
    public Map<String, List<String>> getDbfData(String fieldName) throws IOException {
        if (dbfReader == null) {
            dbfReader = new DataDbfReader(dbfFile);
        }
        return dbfReader.readAllRecordByFieldName(fieldName);

    }

    public DataDbfReader getDbfReader() {
        if (dbfReader == null) {
            dbfReader = new DataDbfReader(dbfFile);
        }
        return dbfReader;
    }

    public void setDbfReader(DataDbfReader dbfReader) {
        this.dbfReader = dbfReader;
    }

    public DataShpReader getShpReader() {
        if (shpReader == null) {
            shpReader = new DataShpReader(shpFile, scaleX, scaleY);
        }
        return shpReader;
    }

    public void setShpReader(DataShpReader shpReader) {
        this.shpReader = shpReader;
    }

    public DataShxReader getShxReader() {
        if (shxReader == null) {
            shxReader = new DataShxReader(shxFile);
        }
        return shxReader;
    }

    public void setShxReader(DataShxReader shxReader) {
        this.shxReader = shxReader;
    }
}
