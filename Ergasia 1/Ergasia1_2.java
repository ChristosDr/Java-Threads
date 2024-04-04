/*
 * HelloThread.java
 *
 * creates threads using a class extending Thread. 
 * 
 */
public class Ergasia1_2 {

    public static void main(String[] args) {

        int numThreads =10;
        MyThread1[] thread1 = new MyThread1[numThreads];
        MyThread2[] thread2 = new MyThread2[numThreads];
        
        /* create and start threads */
        for (int i = 0; i < numThreads; ++i) {
            
            thread1[i] = new MyThread1(i);
            thread2[i] = new MyThread2(i);
            thread1[i].start();
            thread2[i].start();
        }
       
        
        /* wait for threads to finish */
        for (int i = 0; i < numThreads; ++i) {
            try {
                thread1[i].join();
                thread2[i].join();
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
        this.myID = myID;
    }

    public void run() {
        System.out.println("Hello from thread_1 " + MyThread1.currentThread().getName());
    } 

}

class MyThread2 extends Thread {

    private int myID;

    public MyThread2(int myID) {
        this.myID = myID;
    }

    public void run() {
        System.out.println("Hello from thread_2 " + MyThread2.currentThread().getName());
    }

}

