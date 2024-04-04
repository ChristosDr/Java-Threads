/*
 * HelloThread.java
 *
 * creates threads using a class extending Thread. 
 * 
 */
public class Ergasia1_3 {

    public static void main(String[] args) {

        int numThreads =10;
        MyThread1[] thread1 = new MyThread1[numThreads];
        
        /* create and start threads */
        for (int i = 0; i < numThreads; ++i) {
            
            thread1[i] = new MyThread1(i);
            thread1[i].start();
        }
       
        
        /* wait for threads to finish */
        for (int i = 0; i < numThreads; ++i) {
            try {
                thread1[i].join();
            }
            catch (InterruptedException e) {
                System.err.println("this should not happen");
            }
        }
        
        

        System.out.println("In main: threads all done");
    }
}

/* class containing code for each thread to execute */
class MyThread1 extends Thread {

    private int myID;

    public MyThread1(int myID) {
        this.myID = myID+1;
    }

    public void run() {
        //System.out.println("Hello from thread_1 " + MyThread1.currentThread().getName());
        for(int i=1; i<21; i++){
            System.out.println(MyThread1.currentThread().getName()+": "+i+" * "+myID+" = "+i*myID);
        }
    } 

}


