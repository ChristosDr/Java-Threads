import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumIntSeq_4 {

    public static void main(String[] args) {
        long numSteps = 10000000;
        double sum = 0.0;
        int numThreads = Runtime.getRuntime().availableProcessors(); // Get the number of available processors

        /* start timing */
        long startTime = System.currentTimeMillis();

        // Create and start threads
        PiCalculator threads[] = new PiCalculator[numThreads];
        for (int i = 0; i < numThreads; ++i) {
            threads[i] = new PiCalculator(i, numThreads, numSteps);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Calculate the final result
        for (int i = 0; i < numThreads; ++i) {
            sum += threads[i].result;
        }
        double pi = sum * (1.0 / numSteps);

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n", pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }

    static class PiCalculator extends Thread {
        private int id;
        private int numThreads;
        private long numSteps;
        private double result;
        private static Lock lock = new ReentrantLock();

        public PiCalculator(int id, int numThreads, long numSteps) {
            this.id = id;
            this.numThreads = numThreads;
            this.numSteps = numSteps;
        }

        public void run() {
            double partialSum = 0.0;
            double step = 1.0 / numSteps;
            long start = id * (numSteps / numThreads);
            long end = start + (numSteps / numThreads);

            for (long i = start; i < end; ++i) {
                
                lock.lock();
            try {
                double x = ((double) i + 0.5) * step;
                result += 4.0 / (1.0 + x * x);
            } finally {
                
                lock.unlock();
            }
            }

            
            // lock.lock();
            // try {
                //result = partialSum;
            // } finally {
            
            //     lock.unlock();
            // }
        }

        // public double getResult() {
        //     return result;
        // }
    }
}
