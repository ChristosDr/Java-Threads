public class Lab1 {

    static int n = 0; // Orisma timis, Moirazetai os orisma timis, ola ta nimata lamvanoun tin idia timi
    static int[] a; // Orisma anaforas (kai metabliti antikeimenou epeidi einai pinakas?), Isos moirazetai os orisma anaforas giati kathe nima epistrefei stin main mia timi a[i]

    public static void main(String[] args) {

        int numThreads = 4; // Topiki metabliti entos tis main
		
	    a = new int[numThreads];
	    
	    for (int i = 0; i < numThreads; i++)
			a[i] = 0; 
			
		CounterThread counterThreads[] = new CounterThread[numThreads]; // Orisma anaforas
		for (int i = 0; i < numThreads; i++) { // I metabliti i dimiourgeitai gia to for loop
		    counterThreads[i] = new CounterThread(i); // To i moirazetai os orisma timis, kathe nima pairnei diaforetiko i
		    counterThreads[i].start();
		}

        for (int i = 0; i < numThreads; i++) {
            try {
				counterThreads[i].join();
				n = n + counterThreads[i].threadN;
            }
            catch (InterruptedException e) {}
		} 
        for (int i = 0; i < numThreads; i++)
			System.out.println("a["+i+"] = "+a[i]);
			
		System.out.println("n = "+n); 
    }

	static class CounterThread extends Thread {
	  	
		int threadID; // Metabliti klassis
	  int threadN; // Metabliti klassis
		  
	  public CounterThread(int tid) { // To int tid tha to harakteriza os topiki metabliti gia ton kataskevasti? theloume na perasoume tin timi apo tin main sto nima
		  this.threadID = tid;
		  //this.threadN = n;
	  }
			
	  public void run() {
		
		  threadN = threadN + 1 + threadID;  // Topiki metabliti se methodo
		  a[threadID] = a[threadID] + 1 + threadID;
		  System.out.println("Thread "+threadID+" n = "+ threadN +"  a["+threadID+"] ="+ a[threadID]); 
	  }
  }

 }   


// PRIN TO TREXO 
//Perimena kathe nhma na pairnei san timh to ID toy dhladh 1o nhma = 1, 2o nhma =2...
//kai meta sthn main kanoume sum oles tis times ton nhmatwn

//ALLAGES POU EKANA
//evala thn klash CounterThread mesa sthn klash Lab1(tha mporousa na thn afhna kai ejw alla tha dhmiourgousa antikeimena Lab1.n klp)
//Thn ekana static giati mou evgaze error sthn grammh 17
//Esvhsa this.threadN = n, mesa ston constructor, skefthka na to pernaw ws orisma kai auto apo thn main alla den xreiazetai efoson einai static to n

//AFOU TO TREXO
//Phra san apanthsh auto pou perimena
