
public class Lab3 {
  
    public static void main(String[] args) {

        int numThreads = 4;//Topikh sthn main, orisma timhs
		
		SharedCounter count = new SharedCounter(numThreads);//orisma anaforas, metablhth antikeimenou ths klashs SharedCounter, moirazetai ws orisma anaforas se ola ta nhmata
			
		CounterThread counterThreads[] = new CounterThread[numThreads];//orisma anaforas (metablhth antikeimenou Thread?)
		for (int i = 0; i < numThreads; i++) {
		    counterThreads[i] = new CounterThread(i, count);//to count moirazetai ws orisma anaforas 
		    counterThreads[i].start();
		}

        for (int i = 0; i < numThreads; i++) {
            try {
				counterThreads[i].join();
				count.n = count.n + counterThreads[i].threadN;
            }
            catch (InterruptedException e) {}
		} 
        for (int i = 0; i < numThreads; i++)
			System.out.println("a["+i+"] = "+count.a[i]);
			
		System.out.println("n = "+count.n); 
    }
}

class SharedCounter {
	
	int n; //Topikh sthn SharedCounter, orisma timhs
    int[] a;//orisma anaforas
    
    public SharedCounter (int numThreads) {
		
			this.n = 0;
			this.a = new int[numThreads];
	    
			for (int i = 0; i < numThreads; i++)
				this.a[i] = 0; 
	}		

}    

class CounterThread extends Thread {
  	
    int threadID;
    int threadN;
    SharedCounter threadCount;
    
    public CounterThread(int tid, SharedCounter c) {
        this.threadID = tid;
        this.threadCount = c;
        this.threadN = threadCount.n;
        
    }
  	
    public void run() {
  
        threadN = threadN + 1 + threadID;  
        threadCount.a[threadID] = threadCount.a[threadID] + 1 + threadID;
		System.out.println("Thread "+threadID+" n = "+ threadN +"  a["+threadID+"] ="+ threadCount.a[threadID]); 
    }
}
//SKEPTIKO
//Dhmiourgoumai ena antikeimeno ths klasshs SharedCounter kai pername san parametro to 4
//Mesa sth SharedCounter arxikopoioumai ton pinaka a kai dinoume se ola ta stoixeia toy thn timh 0
//Epeita Dhmiourgoume 4 nhmata kai tous pername san orisma to i kai to antikeimeno count
//Opote kathe nhma tha pairnei to ID toy sumfwna me to i dhlahd 1o nhma = 1 klp
//Kai kathe nhma tha exei prosvash ston pinaka a[i] ths klashs SharedCounter, omws to kathe nhma tha exei to diko tou a[i]
//Dhladh 1o nhma a[1], 2o nhma a[2] klp
//Epeita mesa sthn main athrizoume ola ta IDs (a[i] twn nhmatwn) sthn metablhth n ths klashs SharedCounter kai ektupwnoume to sum

//SUMPERASMA
//Mporoume na poume oti h main kai h CounterThread allilepidroun kapws metajh tous mesw ths klashs SharedCounter
