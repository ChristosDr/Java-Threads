public class NumIntSeq_1 {

    public static void main(String[] args) {

        long size = 1000000000;
        double sum = 0.0;
        int numThreads = Runtime.getRuntime().availableProcessors();

        //SharedCounter sharedCounter = new SharedCounter();
        /* start timing */
        long startTime = System.currentTimeMillis();

        double step = 1.0 / size;

        PiCalculator threads[] = new PiCalculator[numThreads];
        for (int i= 0; i < numThreads; i++) {
            threads[i] =  new PiCalculator(i, numThreads, size);
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }
     
        

        
        /* do computation */
        
        for (int i = 0; i < numThreads; ++i) {
            sum += threads[i].getResult();
        }
        double pi = sum * (1.0 / size);

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", size);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
    }

    // static class SharedCounter {
        
    //     private double sum =0;
        

    //     public void Sum(double value) {
    //         sum+=value;
    //     }

    //     public double getSum(){
    //         return sum;
    //     }
    // }

    static class PiCalculator extends Thread
    {
        //private SharedCounter sharedCounter;
        private long myStart;
        private long myStop;
        private double step;
        private long numSteps;
        private double result;
        private int id;
        private int numThreads;

	// constructor
	public PiCalculator(int id, int numThreads, long numSteps)
	{
        this.id = id;
        this.numThreads = numThreads;
        this.numSteps = numSteps;
        this.step = step;
		//this.sharedCounter = sharedCounter;
		myStart = id * (numSteps / numThreads);
		myStop = myStart + (numSteps / numThreads);
       
        //id = myId;
		if (id == (numThreads - 1)) myStop = numSteps;
	}

	// thread code
	public void run()
	{   
        double step = 1.0 / numSteps;
        double partialSum = 0.0;
		for (long i=myStart; i < myStop; ++i) {
            double x = ((double)i+0.5)*step;
            result += 4.0 / (1.0 + x * x);
            
        }
        //result = partialSum;//Super SOS, ejw apo thn for
			
	    }

        public double getResult() {
            return result;
        }

    }
}
