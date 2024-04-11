public class SharedCounterArrayGlobal_Object_2 {

    public static void main(String[] args) {
        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;

        SharedArray sharedArray = new SharedArray(array);

        Object lockObj = new Object();

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(sharedArray, lockObj, end);
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

        public SharedArray(int[] array) {
            this.array = array;
        }

        public void incrementArray(int index, Object lockObj) {
            synchronized (lockObj) {
                for (int j = 0; j < index; j++) {
                    array[index]++;
                }
            }
        }
    }

    static class CounterThread extends Thread {
        private SharedArray sharedArray;
        private Object lockObj;
        private int end;

        public CounterThread(SharedArray sharedArray, Object lockObj, int end) {
            this.sharedArray = sharedArray;
            this.lockObj = lockObj;
            this.end = end;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                sharedArray.incrementArray(i, lockObj);
            }
        }
    }



    
}
