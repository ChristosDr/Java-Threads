import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class SharedCounterArrayGlobal_Lock {

	
    
    public static void main(String[] args) {
        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;

        Lock lock = new ReentrantLock();

        CounterThread threads[] = new CounterThread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(array, end, lock);
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

        for (int i = 0; i < end; i++) {//osa einai ta nhmata toso tha einai kai h kathe thesi tou pinaka
            if (array[i] != numThreads * i) {//dhladh gia 4 nhmata tha exoume 0,4,8,12... pollaplasia tou 4
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads * i);
            }         
        }
        System.out.println(errors + " errors.");
    }


    static class CounterThread extends Thread {
        private int[] array;
        private int end;
        private Lock lock; 
        
        public CounterThread(int[] array, int end, Lock lock) {
            this.array = array;
            this.end = end;
            this.lock = lock; 
        }
        
        public void run() {
            for (int i = 0; i < end; i++) {
                for (int j = 0; j < i; j++){
					lock.lock();
					try { 
						array[i]++;
					} finally {
						lock.unlock() ;
					}
					}
				}
                         
            } 
        }                
}

