import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*; 


public class BruteForceStringMatchThreads {

    public static void main(String args[]) throws IOException {
    
        // if (args.length != 2) {
		// 	System.out.println("BruteForceStringMatch  <file name> <pattern>");
		// 	System.exit(1);
        // }

        long startTime = System.currentTimeMillis();

        String fileString = new String(Files.readAllBytes(Paths.get("C:/Users/chris/Desktop/New folder/file.txt")));//, StandardCharsets.UTF_8);
        char[] text = new char[fileString.length()]; 
        int n = fileString.length();
        for (int i = 0; i < n; i++) { 
            text[i] = fileString.charAt(i); 
        } 
 
        String patternString = new String("tacccagattatcgccatcaacgg");                                                
        char[] pattern = new char[patternString.length()]; 
        int m = patternString.length(); 
        for (int i = 0; i < m; i++) { 
            pattern[i] = patternString.charAt(i); 
        }

        int matchLength = n - m;
        char[] match = new char[matchLength+1]; 
        for (int i = 0; i <= matchLength; i++) { 
            match[i] = '0'; 
        }
        
        //THREADS
        int numThreads = Runtime.getRuntime().availableProcessors();
        MatcherThread threads[] = new MatcherThread[numThreads];

        for (int i= 0; i < numThreads; i++) {
            threads[i] =  new MatcherThread(i, numThreads, matchLength, match, text, pattern,m);
            threads[i].start();
        }
        int matchCount=0;

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                matchCount += threads[i].getMatchCount();
            } catch (InterruptedException e) {}
        }

        /*ArrayList<Integer> match = new ArrayList<Integer>(); */
        


        for (int i = 0; i < matchLength; i++) { 
            if (match[i] == '1') System.out.print(i+" ");
        }
        System.out.println();
        System.out.println("Total matches " + matchCount);

        long endTime = System.currentTimeMillis();
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);
        
   }

   static class MatcherThread extends Thread
   {
       //private SharedCounter sharedCounter;
       private int myStart;
       private int myStop;
       private int id;
       private int numThreads;
       private int matchLength;
       private int matchCount = 0;
       private int m;
       private char[] match;
       private char[] text;
       private char[] pattern;

   // constructor
   public MatcherThread(int id, int numThreads, int matchLength, char[] match, char[] text, char[] pattern, int m )
   {
       this.id = id;
       this.numThreads = numThreads;
       this.matchLength = matchLength;
       this.m = m;
       this.match = match;
       this.text = text;
       this.pattern = pattern;
       myStart = id * (matchLength / numThreads);
       myStop = myStart + (matchLength / numThreads);
      
       if (id == (numThreads - 1)) myStop = matchLength;
   }

   // thread code
   public void run()
   {   
    
    for (int j = myStart; j < myStop; j++) {
        int i;
          for (i = 0; i < m && pattern[i] == text[i + j]; i++);
            if (i >= m) {
                 match[j] = '1';
                matchCount++;
            }        
    }
           
    }

       public double getMatchCount() {
           return matchCount;
       }

   }
}



