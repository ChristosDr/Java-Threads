
public class Lab4 {

    public static void main(String[] args) {

        int numThreads = 4; //Topikh sthn main, orisma timhs
	
	    int n = 0;//Topikh sthn main, orisma timhs
		CounterThread counterThreads[] = new CounterThread[numThreads];//orisma anaforas
		for (int i = 0; i < numThreads; i++) {
		    counterThreads[i] = new CounterThread(i, n);//moirazetai ws orisma timhs ola ta nhmata lambanoun idia timh tou n
		    counterThreads[i].start();
		}
	
        for (int i = 0; i < numThreads; i++) {
            try {
				counterThreads[i].join();
				n += counterThreads[i].threadN;
            }
            catch (InterruptedException e) {}
		} 
		System.out.println("Main n = "+n); 
    }

}

class CounterThread extends Thread {
  	
    int threadID;
    int threadN; 
    
    public CounterThread(int tid, int n) {
        this.threadID = tid;
        this.threadN = n;
    }
  	
    public void run() {
              
        threadN = threadN + 1 + threadID;
        System.out.println("Thread "+threadID+ " n = "+threadN); 
    }
}
//PRIN TO TREXW
//Paromoia me tis prohgoumenes askhseis dhladh kathe nhma tha parei to ID tou
//1o nhma = 1, 2o nhma = 2 klp
//Pername to n se kathe nhma alla einai kapws perito giati se ola ta nhmata dinoume thn timh 0
//Epeita sthn main athrizoume ola ta IDs twn nhmatwn sthn topikh metablhth n, pairnontas apo kathe nhma to threadN tou(counterThreads[i].threadN)
//Kai sthn sunexeia to ektupwnoume

//AFOU TO TREJW
//Phra san apanthsh auto pou perimena