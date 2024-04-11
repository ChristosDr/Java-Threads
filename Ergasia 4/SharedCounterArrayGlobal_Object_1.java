public class SharedCounterArrayGlobal_Object_1 {

    public static void main(String[] args) {
        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;

		SharedArray sharedArray = new SharedArray(array);

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(sharedArray, end);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        check_array(array, end, numThreads);
    }

    static void check_array(int[] array, int end, int numThreads) {
        int errors = 0;

        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            if (array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }



	static class SharedArray {
		private int[] array;
        Object lockObj = new Object();

		public SharedArray(int[] array){
			this.array = array;
		}
		public  void incArr(int index){
			for(int j=0; j<index; j++){
                synchronized (lockObj) {
                    array[index]++;
                }
				
			}
		}
	}

    static class CounterThread extends Thread {
        private SharedArray sharedArray;
        private int end;

        public CounterThread(SharedArray sharedArray, int end) {
            this.sharedArray = sharedArray;
            this.end = end;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                sharedArray.incArr(i);
            }
        }
    }
}
