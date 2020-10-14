// Fredrik Hammar, frha2022
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Factorizer implements Runnable {
	

	private static BigInteger max;
	private static BigInteger factor1 = new BigInteger("0");
	private static BigInteger factor2;
	private static BigInteger product;

	private BigInteger step;
	private BigInteger min;

	Factorizer(BigInteger min, int numThreads) {
		this.min = min;
		this.step = new BigInteger("" + numThreads);
	}

	private synchronized void setFactors(BigInteger number) {
		if (factor1.intValue() != 0) {
			return;
		} else {
			factor1 = number;
			factor2 = product.divide(factor1);
			return;
		}
	}

	public void run() {
		BigInteger number = min;
		while (number.compareTo(max) <= 0) {
			
			if (factor1.intValue() != 0) {
				return;
			}
			
			if (product.remainder(number).compareTo(BigInteger.ZERO) == 0) {
				setFactors(number);
			}

			number = number.add(step);
		}
	}
	
	
	public static boolean returnPrime(BigInteger number) {
	    if (!number.isProbablePrime(5))
	        return false;

	    BigInteger two = new BigInteger("2");
	    if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
	        return false;

	    for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { 
	        if (BigInteger.ZERO.equals(number.mod(i))) 
	            return false;
	    }
	    return true;
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader consoleReader = new BufferedReader(streamReader);
		
		//input product number
		System.out.print("Input number: ");
		String input = consoleReader.readLine();
		product = new BigInteger(input);
		max = product.divide(new BigInteger("2"));
		
		//Check if product is prime
		if (returnPrime(product) == true) {
			System.out.print("No factorization possible");
			System.exit(0);
		}
		
		//input number of threads
		int numProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("You have this many processors: " + numProcessors);
		System.out.print("Input number of threads: ");
	    input = consoleReader.readLine();
		int numThreads = Integer.parseInt(input);

		//Start timer
		long start = System.nanoTime();

        //making the threads
		Thread[] threads = new Thread[numThreads];
		Factorizer[] factorFinders = new Factorizer[numThreads];
		for (int i = 0; i < numThreads; i++) {
			factorFinders[i] = new Factorizer(new BigInteger("2").add(BigInteger.valueOf(i)), numThreads);
			threads[i] = new Thread(factorFinders[i]);
		}
		//threads start
		for (int i = 0; i < numThreads; i++) {
			threads[i].start();
		}
		//threads close
		for (int i = 0; i < numThreads; i++) {
			threads[i].join();
		}

		// Stop timer.
		long stop = System.nanoTime();

		// Output results.
		System.out.println( "Factor1: " + factor1 + "  " + " Factor2: "+factor2 + " Product: " + product);
		System.out.println("Range searched: " + "2" + " - " + max);
		System.out.println("You have this many processors: " + numProcessors);
		System.out.println("Number of threads used: " + numThreads);
		System.out.println("This took: " + (stop - start) / 1.0E9 + " Seconds");

	}

}
