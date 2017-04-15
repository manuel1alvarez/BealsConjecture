

import java.util.*;
import java.io.*;
import java.math.BigInteger;

class Threads implements Runnable {
	private String threadName;
	int start, stop, numBits;

	Threads(String name, int startValue, int stopValue, int numberBits) {
		threadName = name;
		start = startValue;
		stop = stopValue;
		numBits = numberBits;
		System.out.println("Creating " + threadName + " From " + start + " to " + stop);
	}

	public void run() {
		AnswerStructures datagetter = new AnswerStructures();
        datagetter.RunCounterSearch(numBits,start, stop,threadName);
	}


}

public class BealsParallelCounter {
	public static void main(String[] args) {
	int cores = Runtime.getRuntime().availableProcessors();
	AnswerStructures datagetter = new AnswerStructures();
	int numBits = 44; //find counter-examples up to number of bits, range 5-91
	int maxBase = datagetter.MaxBase(numBits); 	
    
    ArrayList<Thread> threads = new ArrayList<Thread>();
    ArrayList<Integer> coreDistributor = Distributor(cores, maxBase) ;
    for (int i = 0; i < cores; i++) {
        threads.add(new Thread(new Threads("Thread " + (i+1), coreDistributor.get(i),  coreDistributor.get(i+1), numBits) ) );
    }
    for (int j = 0; j < threads.size(); j++) {
        threads.get(j).start();
    }
	
	}

	
	
	private static ArrayList<Integer> Distributor(int cores, int maxBase){
	    ArrayList<Integer> coreDis = new ArrayList<Integer>();
	    coreDis.add(0); coreDis.add(maxBase); 
	    if(cores == 1)
	    	return coreDis;
	    
	    int halfBase = (int) (maxBase/2.3);
	    double topCores = Math.round( (.25)*cores) ;
	    coreDis.add(halfBase);
	    
	    for (int i = 0; i < (int) topCores -1; i++){
	        halfBase =  (int) ((int) halfBase/1.7);
	        coreDis.add(halfBase + maxBase/2);
	    }
	    
	    int bottomCores =  (cores - (int) topCores), temp = maxBase/2;
	    for (int i = 0; i < bottomCores -1 ; i++){
	        temp =  (int) ((int) temp/2.5);
	        coreDis.add(temp);
	    }
	    
	    Collections.sort(coreDis);
	    return coreDis;
	    }
}


