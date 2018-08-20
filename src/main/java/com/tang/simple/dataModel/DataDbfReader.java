package com.tang.simple.dataModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataDbfReader extends DataReader {
    private List<Map<String, Object>> fieldList;
    public static String fieldCharset = "gbk";
    private Short recordLength;
    private Integer recordNum;
    private int recordIndex;
    public DataDbfReader(String fileName){
        super(fileName);
        readHeader();
    }

    /**
     *
     * @param recordNum record number start with 1
     * @param fieldName field that formatted in the file
     *                  almost the <code>fieldName</code>
     *                  is like<code>NAME_0</code>,<code>NAME_1</code>and so.
     * @return value specified by fieldName,usually the value store in ascii in file
     * @throws IOException read the may occur IO exception
     */
    private void readRecordByRecordNum(int recordNum, List<String> fieldName,
                                        Map<String, List<String>> recordMap)
            throws IOException{
        if (fieldName.size() == 0) {
            return;
        }
        // record flag
        int deleteFlag = input.read();
        if (deleteFlag == 0x2A){
            // if not reach end
            if (recordNum < this.recordNum) {
                // if not last record, reach next record
                input.skipBytes(Short.toUnsignedInt(recordLength) -1);
            }
            return;
        }

        for (int j = 0; j < fieldList.size(); ++j){
            // 对已保存的field进行计数
            int index = 0;
            Map<String, Object> field = fieldList.get(j);
            String fieldName0 = ((String)field.get("fieldName")).toUpperCase();
            int fieldLength = Byte.toUnsignedInt((byte)field.get("fieldLength"));
            if (fieldName.contains(fieldName0)) {
                List<String> recordList = recordMap.get(fieldName0);
                if (recordList == null) {
                    recordList = new ArrayList<>(fieldName.size());
                }
                recordList.add(dealFieldType(fieldLength));
                recordMap.put(fieldName0, recordList);
                index ++;
                //go to next field
                if (index == fieldList.size() && j + 1 < fieldList.size()) {
                    int countBytes = 0;
                    for (Map<String, Object> map : fieldList.subList(j + 1, fieldList.size())) {
                        countBytes += Byte.toUnsignedInt((byte)map.get("fieldLength"));
                    }
                    input.skipBytes(countBytes);
                    break;
                }
            }else{
                input.skipBytes(fieldLength);
            }
        }

        recordIndex ++;
        if (recordNum > recordIndex) {
            readRecordByRecordNum(recordNum, fieldName, recordMap);
        } else if (recordNum < recordIndex) {
            throw new IOException("record read error, if recordNum " + recordNum + " < 1, may cause this exception.");
        }
    }

    public Map<String, List<String>> readAllRecordByFieldName(String sql) throws IOException {

        // start read record
        Map<String, List<String>> dataMap = new HashMap<>(recordNum);
        // handle sql
        List<String> fieldNames = handleSql(sql);
        for (int i = 1; i <= recordNum; i++) {
            readRecordByRecordNum(i, fieldNames, dataMap);
        }
        // end with 0x1A
        byte end = input.readByte();
        System.out.println("end: " + end);
        return dataMap;
    }

    private List<String> handleSql(String sql) {
        // handle sql
        String[] sqls = sql.split("\\b|,");
        List<String> fieldName = new ArrayList<>(sqls.length);
        for (String s : sqls) {
            fieldName.add(s.toUpperCase());
        }
        return fieldName;
    }

    private byte[] bytes = new byte[256];

    /**
     * 在这里，由于不同类型的字段都是以ascii存储值的，所以在这里没有做更多的处理
     * @param fieldLength 字段的长度
     * @return 字段对应的值
     * @throws IOException 读取数据时可能会发生的io异常
     */
    private String dealFieldType(int fieldLength) throws IOException{

        input.read(bytes, 0, fieldLength);
        return new String(bytes, 0, fieldLength, fieldCharset).trim();
    }

    /**
     * 总是应该先读头部，然后再读取文件记录
     */
    @Override
    public void readHeader() {
        try{

//          version cover one byte
//          int year = input.readByte();
//          int month = input.readByte();
//          int day = input.readByte();
//          data cover 3 bytes
//          skip 4 bytes
            input.skipBytes(4);

            // record number
            recordNum = input.readLittleEndianInt();
            // number of bytes in header
            int headLength = input.readLittleEndianShort();

            // number of bytes in record
            recordLength = input.readLittleEndianShort();
            // reserved 2 bytes, transaction flag 1 byte,
            // encryption flag 1 byte, reserved for dos 12 bytes,
            // mdx file flag 1 byte, language ID 1 byte followed 2 bytes reserved
            // skip altogether 20 bytes
            input.skipBytes(20);

            // field array
            readField((headLength - 32)/32);

            //0x0D as the field descriptor array terminator
            int b = input.readByte();
            if (b != 0x0D){
                throw new IOException("header ending with error");
            }
            // start to read record
            // recordIndex reset
            recordIndex = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read Database field descriptor bytes in the header,
     * every field descriptor cover 32 bytes
     * @param fieldNum field number in header
     */
    private void readField(int fieldNum){
        StringBuilder builder;
        fieldList = new ArrayList<>(fieldNum);
//        byte[] fieldName = new byte[11];
        try {
            int i = 0;
            while (i < fieldNum) {
                builder = new StringBuilder(11);
                Map<String, Object> field = new HashMap<>();
                input.read(bytes, 0, 11);
                for (int j = 0; j < 11; ++j) {
                    if (bytes[j] != 0){
                        builder.append((char)bytes[j]);
                    }
                }
                field.put("fieldName", builder.toString());
                field.put("fieldType", input.readByte());
                // reserved 4 bytes
                // skip 4 bytes
                input.skipBytes(4);
                field.put("fieldLength", input.readByte());
                field.put("fieldPrecision", input.readByte());
                input.skipBytes(2);
                field.put("workAreaID", input.readByte());
                // reserved 10 bytes
                input.skipBytes(10);
                field.put("MDXFlag", input.readByte());

                // add field to amp
                fieldList.add(field);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放这个对象占用的资源
     * @throws IOException 如果发生io异常
     */
    public void close() throws IOException{
        super.close();
        fieldList = null;
    }


    public static void main(String[] args) throws Exception{
        String fileName = "ChinaMap/国界与省界/bou2_4p.dbf";
        DataDbfReader reader = new DataDbfReader(fileName);
//        Map name0 = reader.getByFieldName("NAME_1");
//        for (Map mapModel : (List<Map>) reader.header.get("fieldList")) {
//            System.out.printf("field name: %x\n", mapModel.get("fieldName"));
//        }
//        System.out.println(name0);
//        System.out.println(reader.header);
//        reader.read();
        for (Map mapModel : reader.fieldList) {
                System.out.println(mapModel);
        }
        Map<String, List<String>> map = reader.readAllRecordByFieldName("NAME, GBcode FNODE_");
        System.out.println(map.get("NAME"));
//        for (String mapModel : reader.readAllRecordByFieldName("pinyin")) {
//            System.out.println(mapModel);
//        }
        reader.close();
    }
}
