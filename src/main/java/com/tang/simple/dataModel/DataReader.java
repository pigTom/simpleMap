package com.tang.simple.dataModel;

import com.tang.simple.Utils.PathHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class DataReader {
    MyDataInputStream input;
    Map<String, Object> header;
    DataReader(String fileName) {
        try {
            fileName = PathHelper.getPath(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init(fileName);
    }

    private void init(String fileName){
        try{
            input = new MyDataInputStream(fileName);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHeader(){
        try{

            header = new HashMap<>(12);

            // read fileCode
            header.put("fileCode", input.readBigEndianInt());

            // read unused data four int type stored in 20bytes
            input.skipBytes(20);

            // read file length based bytes
            header.put("fileLength", 2*input.readBigEndianInt());
            // read version
            header.put("version", input.readLittleEndianInt());
            // read shape type
            header.put("shapeType", input.readLittleEndianInt());

            // bounding box
            header.put("minX", input.readLittleEndianDouble());
            header.put("minY", input.readLittleEndianDouble());
            header.put("maxX", input.readLittleEndianDouble());
            header.put("maxY", input.readLittleEndianDouble());

            // 3D
            header.put("minZ", input.readLittleEndianDouble());
            header.put("maxZ", input.readLittleEndianDouble());
            // more
            header.put("minM", input.readLittleEndianDouble());
            header.put("maxM", input.readLittleEndianDouble());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void close() throws IOException {
        if (input != null) {
            input.close();
            input = null;
        }
    }

    public Map<String, Object> getHeader() {
        return header;
    }
    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }
}
