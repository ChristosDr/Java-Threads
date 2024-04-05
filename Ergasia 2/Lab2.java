
   
public class Lab2 {

    public static void main(String[] args) {

        int numThreads = 4;//Topikh sthn main, orisma timhs
		
		int n = 0;//Topikh sthn main, orisma timhs
	    int[] a = new int[numThreads];//Orisma anaforas
	    for (int i = 0; i < numThreads; i++)
			a[i] = 0; 
		
		CounterThread counterThreads[] = new CounterThread[numThreads];//Orisma anaforas
		for (int i = 0; i < numThreads; i++) {
		    counterThreads[i] = new CounterThread(i, n, a);
		    counterThreads[i].start();
		}

        for (int i = 0; i < numThreads; i++) {
            try {
				counterThreads[i].join();
            }
            catch (InterruptedException e) {}
		}
		
		System.out.println("n = "+n);
		
		for (int i = 0; i < numThreads; i++)
			System.out.println("a["+i+"] = "+a[i]); 	
    }

}

class CounterThread extends Thread {
  	
    int threadID;//Metablhth klashs,orisma timhs
    int n;//Metablhth klashs,orisma timhs
    int[] a;//Orisma anaforas
    
    public CounterThread(int tid, int n, int[] a) {
        this.threadID = tid;
        this.n = n;
        this.a = a;
    }
      
    public void run() {
        
        n = n + threadID ;
        a[threadID] = a[threadID] + n ;
        System.out.println("Thread "+threadID+ " my a = "+a[threadID]); 
    }
}
// PRIN TO TREXO 
//Paromoia me Lab1 dhladh kathe nhma na pairnei san timh to ID toy 1o nhma = 0, 2o nhma =1...
//H diafora einai pws to n den einai static kai akomh kai an moirazetai sta nhmata dhmiourgeitai mesa sthn main
//opote h timh allazei mono mesa sta nhmata kai den epistrefetai kati pisw sthn main
//Ara to n tha meinei 0

//ALLAGES POU EKANA
//Sthn grammh 51 eixe n[threadID], den exoume n pinaka alla a

//AFOU TO TREXO
//Phra san apanthsh auto pou perimena
