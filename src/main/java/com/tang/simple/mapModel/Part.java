package com.tang.simple.mapModel;

public class Part {
    int partNum;
    int[] parts;
    public Part(){}
    public Part(int partNum){
        this.partNum = partNum;
        parts = new int[partNum];
    }

    /**
     *设置part的数量，并初始化part
     * @param partNum 设置part的数量，Part为多个点连成的拆线段，如果其父图形为<code>polygon</code>,
     *                则part表示环，首尾相连的折线段。
     */
    public void setPartNum(int partNum) {
        this.partNum = partNum;
        parts = new int[partNum];
    }

    public void setParts(int[] parts){
        this.parts = parts;
    }
}
