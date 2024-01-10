import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname","192.168.31.17");
        try {
            MatrixMultiplication matrixMultiplication = new MatrixMultiplication();
            LocateRegistry.createRegistry(9090);
            Naming.rebind("rmi://localhost:9090/MatrixMultiplication", matrixMultiplication);
        } catch (RemoteException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}