package bmp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

class RandomAccessFileWrapper extends RandomAccessFile {
    public RandomAccessFileWrapper(String name, String mode) throws FileNotFoundException {
        super(name, mode);
    }

    public int WORD() throws IOException {
        return Short.reverseBytes(readShort());
    }

    public int DWORD() throws IOException {
        return Integer.reverseBytes(readInt());
    }

    public long LONG() throws IOException {
        return Long.reverseBytes(readLong());
    }
}

public class Reader {
    public static BitmapPicture read(String filePath) throws IOException {
        RandomAccessFileWrapper buf = new RandomAccessFileWrapper(filePath, "rw");

        BitmapPicture bmp = new BitmapPicture();

        bmp.bitmapFileHeader = readFileHeader(buf);
        bmp.bitmapInfo = readBitmapInfo(buf);

        return bmp;
    }

    private static BitmapFileHeader readFileHeader(RandomAccessFileWrapper buf) throws IOException {
        BitmapFileHeader fh = new BitmapFileHeader();

        fh.type = buf.WORD();
        fh.size = buf.DWORD();

        buf.skipBytes(4);

        fh.offset = buf.DWORD();

        return fh;
    }

    // WORD = readShort
    // DWORD = readInt

    private static BitmapInfo readBitmapInfo(RandomAccessFileWrapper buf) throws IOException {
        BitmapInfo bi = new BitmapInfo();

        bi.biSize = buf.DWORD();
        bi.biWidth = buf.DWORD();
        bi.biHeight = buf.DWORD();
        bi.biPlanes = buf.WORD();
        bi.biBitCount = buf.WORD();
        bi.biCompression = buf.DWORD();
        bi.biSizeImage = buf.DWORD();
        bi.biXPelsPerMeter = buf.DWORD();
        bi.biYPelsPerMeter = buf.DWORD();
        bi.biClrUsed = buf.DWORD();
        bi.biClrImportant = buf.DWORD();

        return bi;
    }
}
