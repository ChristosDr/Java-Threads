import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedCounterArrayGlobalWhile_Lock {
  
    public static void main(String[] args) {
        int end = 10000;
        int[] array = new int[end];
        SharedCounter sharedCounter = new SharedCounter();
        int numThreads = 4;

        Lock lock = new ReentrantLock(); 

        CounterThread threads[] = new CounterThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(array, end, sharedCounter, lock); 
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
        private Lock lock = new ReentrantLock(); 

        public int getNextIndex() {
            lock.lock();
            try {
                return counter++;
            } finally {
                lock.unlock();
            }
        }
    }
    

    static class CounterThread extends Thread {
        private int[] array;
        private int end;
        private SharedCounter sharedCounter; 
        private Lock lock; 

        public CounterThread(int[] array, int end, SharedCounter sharedCounter, Lock lock) {
            this.array = array;
            this.end = end;
            this.sharedCounter = sharedCounter; 
            this.lock = lock; 
        }
  	
        public void run() {
            while (true) {
                int index = sharedCounter.getNextIndex();
                if (index >= end)
                    break;

                //lock.lock(); 
                //try { 
                    array[index]++;
                //} finally {
                //    lock.unlock(); 
               // }
            } 
        }            	
    }
}
