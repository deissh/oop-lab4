import bmp.Reader;

import java.io.RandomAccessFile;

public class Main {
    private static final String filePath = "assets/sample_1280×853.bmp";

    public static void main(String[] args) throws Exception {
        Reader.read(filePath);
    }
}