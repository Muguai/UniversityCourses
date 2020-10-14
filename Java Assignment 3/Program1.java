//Fredrik Hammar, frha2022

package paradis.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;


public class Program1 {
	final static int NUM_WEBPAGES = 40;
	private static WebPage[] webPages = new WebPage[NUM_WEBPAGES];

	private static Thread[] Consumerthreads;
	private static Thread[] Producerthreads;
	private final static PC pc = new PC(webPages);
	
	static int numThreads = 3;

	private static void initialize() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i] = new WebPage(i, "http://www.site.se/page" + i + ".html");
		}
	}

	private static void downloadWebPages() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i].download();
		}
	}

	private static void analyzeWebPages() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i].analyze();
		}
	}

	private static void categorizeWebPages() {
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			webPages[i].categorize();
		}
	}

	private static void presentResult() {
		System.out.print("RESULTS: ");
		for (int i = 0; i < NUM_WEBPAGES; i++) {
			System.out.println(webPages[i]);
		}
	}

	private static void createConsumer(int numThreads) {
		for (int i = 0; i < numThreads; i++) {
			Consumerthreads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						pc.consume();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private static void createProducer(int numThreads) {
		for (int i = 0; i < numThreads; i++) {
			Producerthreads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						pc.produce();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// Initialize the list of webpages.
		initialize();

		// Start timing.
		long start = System.nanoTime();

		Consumerthreads = new Thread[numThreads];
		Producerthreads = new Thread[numThreads];

		createProducer(numThreads);
		createConsumer(numThreads);

		for (int i = 0; i < numThreads; i++) {
			Consumerthreads[i].start();
			Producerthreads[i].start();

		}

		for (int i = 0; i < numThreads; i++) {
			Consumerthreads[i].join();
			Producerthreads[i].join();

		}

		// Stop timing.
		long stop = System.nanoTime();

		// Present the result.
		presentResult();

		// Present the execution time.
		System.out.println("Execution time (seconds): " + (stop - start) / 1.0E9);
	}
}

class PC {
	int capacity = 5;

	BlockingQueue<Consumer<WebPage>> ProducerConsumerqueue = new ArrayBlockingQueue<Consumer<WebPage>>(capacity);
	WebPage[] webby;
	int NumberOfWebPages;

	PC(WebPage[] web) {
		this.webby = web;
		this.NumberOfWebPages = web.length;
	}

	boolean ProduceStop = true;
	boolean ConsumerStop = true;

	int counter = 0;
	int consumerCounter = 0;
	int action = 1;

	// Function called by producer thread
	public void produce() throws InterruptedException {
		while (ProduceStop) {
			synchronized (this) {

				// producer thread waits while list
				// is full
				while (ProducerConsumerqueue.size() == capacity) {
					wait();

				}
				// insert the jobs in the list

				Consumer<WebPage> DoThis = null;
				if (action == 1) {
					DoThis = WebPage -> {
						WebPage.download();
					};
					counter++;
					ProducerConsumerqueue.add(DoThis);
				} else if (action == 2) {
					DoThis = WebPage -> {
						WebPage.analyze();
						;
					};
					counter++;
					ProducerConsumerqueue.add(DoThis);
				} else if (action == 3) {
					DoThis = WebPage -> {
						WebPage.categorize();
					};
					counter++;
					ProducerConsumerqueue.add(DoThis);
				}

				if (counter >= (NumberOfWebPages)) {
					counter = 0;
					action++;
					System.out.println("increase Action");

					if (action > 3) {
						System.out.println("producer STOP");

						ProduceStop = false;
					}

				}

			}

		}

	}

	// Function called by consumer thread
	public void consume() throws InterruptedException {
		while (ConsumerStop) {
			Consumer<WebPage> val = null;
			WebPage page = null;
			// wait til the queue has a task for me
			if (!ProducerConsumerqueue.isEmpty()) {
				synchronized (this) {
					// Get a job and a unique webPage to do that task on
					val = ProducerConsumerqueue.remove();
					page = webby[consumerCounter++];
					if (consumerCounter == (NumberOfWebPages)) {
						System.out.println("Reset consumer counter");
						consumerCounter = 0;
					}

					notify();

				}
				synchronized (page) {
					// consumer the Job
					val.accept(page);
					if (action > 3 && ProducerConsumerqueue.isEmpty()) {
						System.out.println("Consumer STOP");

						ConsumerStop = false;
					}

				}

			}

		}

	}
}
