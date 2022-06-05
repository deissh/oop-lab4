import java.io.IOException;
import java.rmi.Remote;

public interface Compressor extends Remote {
    byte[] compress(byte[] source) throws IOException;
}