import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MatrixMultiplication extends UnicastRemoteObject implements MatrixMultiplicationInterface {
    protected MatrixMultiplication() throws RemoteException {
        super();
    }

    @Override
    public int[][] forkJoinMultiplication(int[][] _matrix, int _factor, int v_from, int v_to) throws RemoteException {
        System.out.println("Procesing Fork Join multiplication");
        ForkJoinMultiplication forkJoinMultiplication = new ForkJoinMultiplication(_matrix, _factor, v_from, v_to);
        ForkJoinPool forkJoinPool;
        try {
            forkJoinPool = ForkJoinPool.commonPool();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        forkJoinPool.invoke(forkJoinMultiplication);
        return _matrix;
    }

    @Override
    public int[][] executorServiceMultiplication(int[][] _matrix, int _factor) throws RemoteException {
        System.out.println("Procesing Executor service multiplication");
        ExecutorService executorService = Executors.newWorkStealingPool();
        ExecutorMultiplication.multiplyMatrix(_matrix, _factor, executorService);
       return _matrix;
    }

    @Override
    public int[][] sequentialMultiplication(int[][] _matrix, int _factor) throws RemoteException {
        System.out.println("Procesing Sequential multiplication");
        return SequentialMultiplication.multiplyMatrix(_matrix, _factor);
    }
}
