import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumIntSeq_2 {

    public static void main(String[] args) {

        long size = 10000;
        double sum = 0.0;
        int numThreads = Runtime.getRuntime().availableProcessors();

        SharedCounter sharedCounter = new SharedCounter();
        /* start timing */
        long startTime = System.currentTimeMillis();

        double step = 1.0 / size;

        PiCalculator threads[] = new PiCalculator[numThreads];
        for (int i= 0; i < numThreads; i++) {
            threads[i] =  new PiCalculator(i, numThreads, size,step, sharedCounter);
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }
     
        

        
        /* do computation */
        
        double pi = sharedCounter.getSum() * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", size);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }

    static class SharedCounter {
        
        private double sum =0;
        private Lock lock = new ReentrantLock();

        public void Sum(double value) {
            lock.lock();
            try {
                sum+=value;
            } finally {
                lock.unlock();
            }
            
        }

        public double getSum(){
            return sum;
        }
    }

    static class PiCalculator extends Thread
    {
        private SharedCounter sharedCounter;
        private long myStart;
        private long myStop;
        private double step;
        //private int id;

	// constructor
	public PiCalculator(int myId, int numThreads,  long size, double step, SharedCounter sharedCounter)
	{
		this.sharedCounter = sharedCounter;
		myStart = myId * (size / numThreads);
		myStop = myStart + (size / numThreads);
        this.step = step;
        //id = myId;
		if (myId == (numThreads - 1)) myStop = size;
	}

	// thread code
	public void run()
	{   
        double sum=0.0;
		for (long i=myStart; i < myStop; ++i) {
            double x = ((double)i+0.5)*step;
            sum += 4.0/(1.0+x*x);
            sharedCounter.Sum(sum);
        }
        //Super SOS, ejw apo thn for
			
	}
    }
}
