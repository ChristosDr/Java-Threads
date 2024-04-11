public class SharedCounterArrayGlobalWhile_Object {

    public static void main(String[] args) {
        int end = 10000;
        int numThreads = 4;
        int[] array = new int[end];

        SharedCounter sharedCounter = new SharedCounter();
        Object lockObj = new Object(); 

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(sharedCounter, array, end, lockObj);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        check_array(array, end);
    }

    static void check_array(int[] array, int end) {
        int errors = 0;
        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            if (array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class SharedCounter {
        private int counter = 0;

        public synchronized int getNextIndex() {
            return counter++;
        }

        public synchronized void incArr(int[] array, int index) {
            array[index]++;
        }
    }

    static class CounterThread extends Thread {
        private SharedCounter sharedCounter;
        private int[] array;
        private int end;
        private Object lockObj;

        public CounterThread(SharedCounter sharedCounter, int[] array, int end, Object lockObj) {
            this.sharedCounter = sharedCounter;
            this.array = array;
            this.end = end;
            this.lockObj = lockObj;
        }

        public void run() {
            while (true) {
                int index;
                synchronized (lockObj) {
                    if (sharedCounter.counter >= end)
                        break;
                    index = sharedCounter.getNextIndex();
                }
                sharedCounter.incArr(array, index);
            }
        }
    }
}
