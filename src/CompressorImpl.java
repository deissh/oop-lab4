import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;


public class CompressorImpl
        extends java.rmi.server.UnicastRemoteObject
        implements Compressor
{
    public CompressorImpl() throws java.rmi.RemoteException {
        super();
    }

    public byte[] compress(byte[] source) throws IOException {
        if (source.length <= 1) {
            throw new RemoteException("invalid size");
        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(source);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(ImageIO.read(inputStream), "jpeg", outputStream);
        File temp = File.createTempFile("source", null);
        RandomAccessFile buf = new RandomAccessFile(temp, "rw");

        buf.setLength(source.length);
        buf.seek(0);
        buf.write(source);

        buf.seek(0x0A);
        int offset = Integer.reverseBytes(buf.readInt());

        buf.seek(0x0E);
        int bmpHeaderSize = Integer.reverseBytes(buf.readInt());
        if (bmpHeaderSize != 124 && bmpHeaderSize != 108) {
            return source;
        }

        buf.seek(0x1C);
        buf.writeInt(Integer.reverseBytes(0));

        buf.seek(0x1E);
        buf.writeInt(Integer.reverseBytes(4));

        buf.seek(0x22);
        buf.writeInt(Integer.reverseBytes(outputStream.size()));

        buf.setLength(offset);
        buf.seek(offset);
        buf.write(outputStream.toByteArray());

        byte[] response = new byte[(int) buf.length()];
        buf.seek(0);
        buf.readFully(response);

        buf.close();
        return response;
    }
}
