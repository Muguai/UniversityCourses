//Fredrik Hammar, frha2022
package paradis.assignment3;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;  



public class Program2 {
	final static int NUM_WEBPAGES = 40;
    private static ArrayList<WebPage> list = new ArrayList<WebPage>();



	private static void initialize() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			list.add( new WebPage(i, "http://www.site.se/page" + i + ".html"));
		}
	}
	
	private static void downloadWebPages() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
	//		webPages[i].download();
		}
	}
	
	private static void analyzeWebPages() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
		//	webPages[i].analyze();
		}
	}
	
	private static void categorizeWebPages() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
		//	webPages[i].categorize();
		}
	}
	
	private static void presentResult() {
		System.out.print("RESULTS: ");
		for (int i = 0; i < NUM_WEBPAGES; i++) {
		//	System.out.println(webPages[i]);
		}
	}
	
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// Initialize the list of webpages.
		initialize();

		 // Object of a class that has both produce() 
        // and consume() methods 

	
		// Start timing.
		long start = System.nanoTime();
		
		    
	    Thread tDownload1 = new Thread(() -> list.parallelStream().forEach(a -> { a.download();  }));
	    Thread tDownload2 = new Thread(() -> list.parallelStream().forEach(a -> { a.download();  }));

	    
	    Thread tAnalyze1 = new Thread(() -> list.parallelStream().forEach(a -> { a.analyze();  }));
	    Thread tAnalyze2 = new Thread(() -> list.parallelStream().forEach(a -> { a.analyze();  }));
	    
	    Thread tCatergorize1 = new Thread(() -> list.parallelStream().forEach(a -> { a.categorize();  }));
	    Thread tCategorize2 = new Thread(() -> list.parallelStream().forEach(a -> { a.categorize();  }));

	    tDownload1.start();
	    tDownload2.start();
	    tDownload1.join();
	    tDownload2.join();
	    
	    tAnalyze1.start();
	    tAnalyze2.start();
	    tAnalyze1.join();
	    tAnalyze2.join();
	    
	    tCatergorize1.start();
	    tCategorize2.start();
	    tCatergorize1.join();
	    tCategorize2.join();
	    

		// Stop timing.
		long stop = System.nanoTime();
		

		// Present the result.
		presentResult();
		
		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop - start) / 1.0E9);
	}
}
