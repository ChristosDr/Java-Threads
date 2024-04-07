import java.lang.Math;

class SimpleSat {
	
    public static void main(String[] args) {  
    
        // Circuit input size (number of bits)
        int size = 28;
        int numThreads = Runtime.getRuntime().availableProcessors();
        // Number of possible inputs (bit combinations)
        int iterations = (int) Math.pow(2, size);
        
        // Start timing
        long start = System.currentTimeMillis();
        
        SqrtGroupThread threads[] = new SqrtGroupThread[numThreads];
        for (int i= 0; i < numThreads; i++) {
            threads[i] =  new SqrtGroupThread(i, numThreads, iterations, size);
            threads[i].start();
        }


        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }
        // Stop timing   
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        
        System.out.println ("All done\n");
        System.out.println("time in ms = "+ elapsedTimeMillis);
        
    }

    static class SqrtGroupThread extends Thread
    {

        private int myStart;
        private int myStop;
        private int secondSize;
        //private int id;

	// constructor
	public SqrtGroupThread(int myId, int numThreads,  int size, int sizeMe)
	{
		
		myStart = myId * (size / numThreads);
		myStop = myStart + (size / numThreads);
        secondSize = sizeMe;
        //id = myId;
		if (myId == (numThreads - 1)) myStop = size;
	}

	// thread code
	public void run()
	{
		for(int i=myStart; i<myStop; i++) { 
            check_circuit (i, secondSize);
        }
			
	}
    }
        
    static void check_circuit (int z, int size) {
    
        // z: the combination number
        // v: the combination number in binar format 
        
		boolean[] v = new boolean[size];  
    
		for (int i = size-1; i >= 0; i--) 
			v[i] = (z & (1 << i)) != 0;
    
        // Check the ouptut of the circuit for input v
        boolean value = 
           (  v[0]  ||  v[1]  )
        && ( !v[1]  || !v[3]  )
        && (  v[2]  ||  v[3]  )
        && ( !v[3]  || !v[4]  )
        && (  v[4]  || !v[5]  )
        && (  v[5]  || !v[6]  )
        && (  v[5]  ||  v[6]  )
        && (  v[6]  || !v[15] )
        && (  v[7]  || !v[8]  )
        && ( !v[7]  || !v[13] )
        && (  v[8]  ||  v[9]  )
        && (  v[8]  || !v[9]  )
        && ( !v[9]  || !v[10] )
        && (  v[9]  ||  v[11] )
        && (  v[10] ||  v[11] )
        && (  v[12] ||  v[13] )
        && (  v[13] || !v[14] )
        && (  v[14] ||  v[15] )
        && (  v[14] ||  v[16] )
        && (  v[17] ||  v[1]  )
        && (  v[18] || !v[0]  )
        && (  v[19] ||  v[1]  )
        && (  v[19] || !v[18] )
        && ( !v[19] || !v[9]  )
        && (  v[0]  ||  v[17] )
        && ( !v[1]  ||  v[20] )
        && ( !v[21] ||  v[20] )
        && ( !v[22] ||  v[20] )
        && ( !v[21] || !v[20] )
        && (  v[22] || !v[20] );
        
        // If output == 1 print v and z
        if (value) {
			printResult(v, size, z);
		}	
    }
    
    // Printing utility
    static void printResult (boolean[] v, int size, int z) {
		
		String result = null;
		result = String.valueOf(z);

		for (int i=0; i< size; i++)
			if (v[i]) result += " 1";
			else result += " 0";
		
		System.out.println(result);
	}	
    
}
