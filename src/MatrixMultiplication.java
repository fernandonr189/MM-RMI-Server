import java.nio.channels.SelectionKey;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MatrixMultiplication extends UnicastRemoteObject implements MatrixMultiplicationInterface {

    private long duration;
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
        long start = System.nanoTime();
        forkJoinPool.invoke(forkJoinMultiplication);
        long finish = System.nanoTime();
        duration = finish - start;
        return _matrix;
    }

    @Override
    public int[][] executorServiceMultiplication(int[][] _matrix, int _factor) throws RemoteException {
        System.out.println("Procesing Executor service multiplication");
        ExecutorService executorService = Executors.newWorkStealingPool();
        long start = System.nanoTime();
        ExecutorMultiplication.multiplyMatrix(_matrix, _factor, executorService);
        long finish = System.nanoTime();
        duration = finish - start;
        return _matrix;
    }

    @Override
    public int[][] sequentialMultiplication(int[][] _matrix, int _factor) throws RemoteException {
        System.out.println("Procesing Sequential multiplication");
        long start = System.nanoTime();
        int[][] result = SequentialMultiplication.multiplyMatrix(_matrix, _factor);
        long finish = System.nanoTime();
        duration = finish - start;
        return result;
    }

    @Override
    public long getDuration() {
        return duration;
    }
}
