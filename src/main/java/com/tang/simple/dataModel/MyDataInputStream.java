package com.tang.simple.dataModel;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MyDataInputStream extends RandomAccessFile
{
    public MyDataInputStream(String fileName) throws FileNotFoundException{
        super(fileName, "r");
    }

    public int readBigEndianInt() throws IOException {
        return this.readInt();
    }
    public int readLittleEndianInt() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1);
    }

    /**
     * read next eight bytes in big endian way to interpreted as long
     * @return the next eight bytes of this input stream interpreted as
     * <code>long</code>
     * @throws IOException 可能会抛出IO异常
     */
    public long readBigEndianLong() throws IOException {
        return this.readLong();
    }

    private byte[] readBuffer = new byte[8];

    /**
     * read next eight bytes in little endian way to interpreted as long
     * @return the next eight bytes of this input stream interpreted as
     * <code>long</code>
     * @throws IOException 可能会抛出IO异常
     */
    public long readLittleEndianLong() throws IOException {
        readFully(readBuffer, 0, 8);
        return (((long)readBuffer[7] << 56) +
                ((long)(readBuffer[6] & 255) << 48) +
                ((long)(readBuffer[5] & 255) << 40) +
                ((long)(readBuffer[4] & 255) << 32) +
                ((long)(readBuffer[3] & 255) << 24) +
                ((readBuffer[2] & 255) << 16) +
                ((readBuffer[1] & 255) <<  8) +
                (readBuffer[0] & 255));
    }

    /**
     * read next eight bytes in big endian way to interpreted as double
     * @return read next eight bytes of this input steam interpreted as
     * <code>double</code>
     * @throws IOException 可能会抛出IO异常
     */
    public Double readBigEndianDouble() throws IOException {
        return this.readDouble();
    }

    /**
     * read next eight bytes in little endian way to interpreted as double
     * @return read next eight bytes of this input stream interpreted as
     * <code>double</code>
     * @throws IOException 可能会抛出IO异常
     */
    public Double readLittleEndianDouble() throws IOException {
        return Double.longBitsToDouble(readLittleEndianLong());
    }

    public short readBigEndianShort() throws IOException {
        return this.readShort();
    }

    public short readLittleEndianShort() throws IOException {
        int sh1 = read();
        int sh2 = read();
        return (short)((sh2 << 8) + sh1);
    }
}

