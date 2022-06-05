import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.RandomAccessFile;

public class Main {
    private static final String filePath = "assets/sample_1280×853.bmp";

    public static void main(String[] args) throws Exception {
        BufferedImage data = ImageIO.read(new File(filePath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        RandomAccessFile buf = new RandomAccessFile(filePath, "rw");

        buf.seek(0x0A);
        int offset = Integer.reverseBytes(buf.readInt());

        buf.seek(0x0E);
        int bmpHeaderSize = Integer.reverseBytes(buf.readInt());
        if (bmpHeaderSize != 124) {
            System.out.println("only bmp with BITMAPV5HEADER");
            return;
        }

        buf.seek(0x1C);
        buf.writeInt(Integer.reverseBytes(0));

        buf.seek(0x1E);
        buf.writeInt(Integer.reverseBytes(4));


        ImageIO.write(data, "jpeg", bos);

        buf.seek(offset);
        buf.write(bos.toByteArray());

        buf.close();
    }
}