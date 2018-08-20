package com.tang.simple.dataModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataShxReader extends DataReader{
    private List<Integer> offsetList;

    /**
     * 实例化DataShxReader，如果头为空，就要读取文件的头部
     * @param fileName 文件的路径
     */
    public DataShxReader(String fileName){
        super(fileName);
        if (header == null) {
            readHeader();

        }
    }

    /**
     * 读取整个shx文件，并返回整个偏移列表
     * @return 返回对应的shp文件的偏移值列表
     * @throws IOException
     */
    public List<Integer> readOffsetList() throws IOException{
        Integer fileLength = (int)header.get("fileLength");
        int length = (fileLength-100)/8;
        offsetList = new ArrayList<>(length);
        int i = 0;
        while (i < length) {
            offsetList.add(readRecordOffset());
            i++;
        }
        return offsetList;
    }

    /**
     * read record offset according to record number
     * for example if the <code>recordNumber</code> = 1
     * returned value will be 100( the file header is 100 bytes long)
     * @param recordNumber  record number start with 1
     * @return
     */
    public int readSpecifiedRecordOffset(int recordNumber){
        int recordOffset = 0;
        try{
            input.seek((recordNumber - 1)  * 8 + 100);
            recordOffset = readRecordOffset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  recordOffset;
    }

    private Integer readRecordOffset(){
        int length = 0;
        try{
            length = input.readBigEndianInt();
            // skip unused Content Length
            input.skipBytes(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return length * 2;
    }

    public void close() throws IOException{
        offsetList = null;
        super.close();
    }
}
