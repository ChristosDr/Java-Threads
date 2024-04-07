/* Matrix Addition A = B + C                       */

class MatrixAdd
{
  public static void main(String args[])
  {
    int size = 10;
    int numThreads = Runtime.getRuntime().availableProcessors();
    
    double[][] a = new double[size][size];
    double[][] b = new double[size][size];
    double[][] c = new double[size][size];
    for(int i = 0; i < size; i++) 
      for(int j = 0; j < size; j++) { 
        a[i][j] = 0.1;
		    b[i][j] = 0.3;
        c[i][j] = 0.5;
      }  
    
    long start = System.currentTimeMillis(); 

    SqrtGroupThread threads[] = new SqrtGroupThread[numThreads];
    
    for(int i = 0; i < numThreads; i++) {
        threads[i] =  new SqrtGroupThread(i, numThreads, a, b ,c , size);
        threads[i].start();
      }
      // for(int j = 0; j < size; j++)  
      //   a[i][j] = b[i][j] + c[i][j];
    
      for(int i = 0; i < numThreads; i++) {
        try {
            threads[i].join();
        } catch (InterruptedException e) {}
    }

    long elapsedTimeMillis = System.currentTimeMillis()-start;
    System.out.println("time in ms = "+ elapsedTimeMillis);
    // for debugging 
    // for(int i = 0; i < size; i++) { 
    //   for(int j = 0; j < size; j++) 
    //     System.out.print(a[i][j]+" "); 
    //   System.out.println();
    // }    
  }



  static class SqrtGroupThread extends Thread
    {
        private double[][] a ;
        private double [][] b;
        private double [][] c;
        private int myStart;
        private int myStop;
        private int size;
        private int myId;
        //private int id;

	// constructor
	public SqrtGroupThread(int myId, int numThreads, double[][] a, double[][] b, double[][] c, int size)
	{
		this.a = a;
    this.b = b;
    this.c = c;
    this.size = size;
    // this.myId = myId;
    block = (size / numThreads);
		myStart = myId * (size / numThreads);
		myStop = myStart + (size / numThreads);
        //id = myId;
		if (myId == (numThreads - 1)) myStop = size;
	}

	// thread code
  public void run()
  {
    for(int i=myStart; i<myStop; i++) {
      for(int j=0; j<size; j++ ){
        //System.out.println("Thread: "+id +" with i "+ i);
        a[i][j] = b[i][j] + c[i][j];
        System.out.print(a[i][j]+" "); 
      }
      //System.out.println("MyStart: "+myStart+" and myStop: "+myStop+" and Thread-"+myId);
      System.out.println();
            
    }
      
  }
    }
}
