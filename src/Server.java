import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(1099);

        try {
            System.out.println("stating...");
            Naming.rebind("rmi://localhost:1099/CompressorService", new CompressorImpl());
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}
