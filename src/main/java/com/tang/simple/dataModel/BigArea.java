package com.tang.simple.dataModel;

import com.tang.simple.Utils.MapTranslator;
import com.tang.simple.Utils.PathHelper;
import com.tang.simple.mapModel.RecordList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

/**
 * all geometric data will be stored in this class
 */
public class BigArea {

    private String nationBoundLinePath;
    private String nationBoundPolyPath;
    private String provinceBoundLinePath;
    private String provinceBoundPolyPath;
    private String cityBoundPath;
    private String cityResidentPath;
    private String capitalPath;
    private String riverLevel1PolyPath;
    private String riverLevel1LinePath;
    private String highwayPath;
    private String railwayPath;
    private String standardNationPath;

    private static class SingleInstance{
        private static final BigArea bigArea = new BigArea();
    }
    public static BigArea newInstance() {
        return SingleInstance.bigArea;
    }
    BigArea() {
        this("map.properties");
    }
    BigArea(String mapProperties){
        Properties properties = new Properties();
        InputStream stream = null;
        InputStreamReader fileInputStream = null;
        try {
            stream = BigArea.class.getClassLoader().getResourceAsStream(mapProperties);
            if (stream == null) {
                throw new IOException("map property read error");
            }
            // 用InputStreamReader能够读取中文
            fileInputStream = new InputStreamReader(stream, "utf-8");
            properties.load(fileInputStream);
            nationBoundLinePath = properties.getProperty("nation_bound_line");
            nationBoundPolyPath = properties.getProperty("nation_bound_poly");
            provinceBoundLinePath = properties.getProperty("province_bound_line");
            provinceBoundPolyPath = properties.getProperty("province_bound_poly");
            cityBoundPath = properties.getProperty("city_bound");
            cityResidentPath = properties.getProperty("city_resident");
            capitalPath = properties.getProperty("capital");
            riverLevel1PolyPath = properties.getProperty("river_level1P");
            riverLevel1LinePath = properties.getProperty("river_level1L");
            highwayPath = properties.getProperty("highway");
            railwayPath = properties.getProperty("railway");
            standardNationPath = properties.getProperty("standard_nation_bound");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void setMapBounds(MapTranslator translator) {
        setMapBounds(standardNationPath, translator);
    }

    public void setMapBounds(String path, MapTranslator translator) {
        path = PathHelper.getPath(path);
        if (path.endsWith("txt")) {
            Map<String, Double> header = new WaterData(path, null).readHeader();
            translator.minX = header.get("xllcorner");
            translator.minY = header.get("yllcorner");
            translator.maxX = header.get("cellsize") * header.get("ncols") + translator.minX;
            translator.maxY = header.get("cellsize") * header.get("nrows") + translator.minY;
            translator.setScale();
        } else {
            Map<String, Object> header = new DataHelper(path, null, null).getShxReader().getHeader();
            translator.maxX = (Double)header.get("maxX") ;
            translator.minX = (Double)header.get("minX");
            translator.maxY = (Double)header.get("maxY");
            translator.minY = (Double)header.get("minY");
            translator.setScale();
        }

    }
    public RecordList getNationBoundLine(Double scaleX, Double scaleY) throws IOException{
        return getRecordByPath(nationBoundLinePath, scaleX, scaleY);
    }
    public RecordList getProvinceBoundPoly(Double scaleX, Double scaleY) throws IOException{
        DataHelper helper = new DataHelper(provinceBoundPolyPath, scaleX, scaleY);
        RecordList recordList = helper.getRecords();
        recordList.setNames(helper.getDbfData("NAME").get("NAME"));
        return recordList;
    }
    public RecordList getProvinceBoundLine(Double scaleX, Double scaleY)throws IOException {
        return getRecordByPath(provinceBoundLinePath, scaleX, scaleY);
    }
    public RecordList getNationBoundPoly(Double scaleX, Double scaleY)throws IOException {
        return getRecordByPath(nationBoundPolyPath, scaleX, scaleY);
    }
    public RecordList getCapital(Double scaleX, Double scaleY) throws IOException {
        return getRecordByPath(capitalPath, scaleX, scaleY);
    }
    public RecordList getCityBound(Double scaleX, Double scaleY) throws IOException {
        return getRecordByPath(cityBoundPath, scaleX, scaleY);
    }
    public RecordList getcityResident(Double scaleX, Double scaleY) throws IOException {
        return getRecordByPath(cityResidentPath, scaleX, scaleY);
    }
    public RecordList getRiverLevel1Poly(Double scaleX, Double scaleY) throws IOException {
        return getRecordByPath(riverLevel1PolyPath, scaleX, scaleY);
    }
    public RecordList getRiverLevel1Line(Double scaleX, Double scaleY) throws IOException {
        return getRecordByPath(riverLevel1LinePath, scaleX, scaleY);
    }
    public RecordList getRailway(Double scaleX, Double scaleY) throws IOException {
        return getRecordByPath(railwayPath, scaleX, scaleY);
    }
    public RecordList getHighway(Double scaleX, Double scaleY) throws IOException {
        return getRecordByPath(highwayPath, scaleX, scaleY);
    }

    public RecordList getRecordListByPath(String path, Double scaleX, Double scaleY)throws IOException{
        return new DataHelper(path, scaleX, scaleY).getRecords();
    }

    private RecordList getRecordByPath(String path, Double scaleX, Double scaleY) throws IOException{
        return new DataHelper(path, scaleX, scaleY).getRecords();
    }
}
