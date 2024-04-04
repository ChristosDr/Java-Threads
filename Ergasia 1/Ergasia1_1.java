public class Ergasia1_1 {

    public static void main(String[] args) {

      
        MyThread1 thread1 = new MyThread1();
        MyThread2 thread2 = new MyThread2();
    
       
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }
        catch (InterruptedException e) {
            System.err.println("this should not happen");
        }
        

        System.out.println("In main: threads all done");
    }
}

/* class containing code for each thread to execute */
class MyThread1 extends Thread {

    /* thread code */
    public void run() {
        System.out.println("Hello from thread_1 " + MyThread1.currentThread().getName());
    } 

}

class MyThread2 extends Thread {

    /* thread code */
    public void run() {
        System.out.println("Hello from thread_2 " + MyThread2.currentThread().getName());
    } 

}

