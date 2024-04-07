/* Vector Addition a = b + c                       */

class VectorAdd
{
  public static void main(String args[])
  {
    int size = 1000;
    int numThreads = Runtime.getRuntime().availableProcessors();
    
    double[] a = new double[size];
    double[] b = new double[size];
    double[] c = new double[size];

    for(int i = 0; i < size; i++) {
        a[i] = 0.0;
		b[i] = 1.0;
        c[i] = 0.5;
    }

    long start = System.currentTimeMillis();

    SqrtGroupThread threads[] = new SqrtGroupThread[numThreads];
		
    for (int i= 0; i < numThreads; i++) {
        threads[i] =  new SqrtGroupThread(i, numThreads, a, b ,c , size);
        threads[i].start();
    }

    for(int i = 0; i < numThreads; i++) {
        try {
            threads[i].join();
        } catch (InterruptedException e) {}
    }

    long elapsedTimeMillis = System.currentTimeMillis()-start;
	System.out.println("time in ms = "+ elapsedTimeMillis);

    
  }



  static class SqrtGroupThread extends Thread
    {
        private double [] a;
        private double [] b;
        private double [] c;
        private int myStart;
        private int myStop;
        //private int id;

	// constructor
	public SqrtGroupThread(int myId, int numThreads, double[] a, double[] b, double[] c, int size)
	{
		this.a = a;
        this.b = b;
        this.c = c;
		myStart = myId * (size / numThreads);
		myStop = myStart + (size / numThreads);
        //id = myId;
		if (myId == (numThreads - 1)) myStop = size;
	}

	// thread code
	public void run()
	{
		for(int i=myStart; i<myStop; i++) {
            //System.out.println("Thread: "+id +" with i "+ i);
            a[i] = b[i] + c[i];
        }
			
	}
    }
}
