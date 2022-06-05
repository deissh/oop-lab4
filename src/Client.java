import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            Compressor svc = (Compressor) Naming.lookup("rmi://localhost:1099/CompressorService");

            byte[] raw = Files.readAllBytes(Paths.get("assets/sample_1280×853.bmp"));

            byte[] response = svc.compress(raw);
            Files.write(
                    Paths.get("assets/sample_1280×853_compressed.bmp"),
                    response,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (Exception e) {
            System.out.println("err: " + e.getMessage());
        }
    }
}
