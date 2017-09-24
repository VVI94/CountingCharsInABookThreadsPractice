import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import treads.FinderThread;
import treads.TextPart;

public class OccurancesOfACharInText {
	private static final int COUNT_OF_PARTS_AND_THREADS = 5;

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		String text = new String(readingFileExtractingString());
		
		// mainThread
		System.out.println("=============== Using main thread ===============");
		long startMain = System.currentTimeMillis();

		System.out.println("Occurances in main thread: " + countOccurrences(text, ','));

		System.out.println("Main time: " + (double) ((System.currentTimeMillis() - startMain) / 1000.0));

		// One thread
		System.out.println("=============== Using one thread ===============");
		long startOneThread = System.currentTimeMillis();
		Runnable oneThread = new FinderThread(text);
		oneThread.run();
		System.out.println("One thread time: " + (double) ((System.currentTimeMillis() - startOneThread) / 1000.0));

		// With threadpool
		System.out.println("=============== Using five threads ===============");
		long start5Threads = System.currentTimeMillis();

		ExecutorService threadPool = Executors.newFixedThreadPool(COUNT_OF_PARTS_AND_THREADS);

		for (int startIndex = 0, end = 1; startIndex < COUNT_OF_PARTS_AND_THREADS; startIndex++, end++) {
			threadPool.submit(new TextPart(text.substring(text.length() / COUNT_OF_PARTS_AND_THREADS * startIndex, text.length() / COUNT_OF_PARTS_AND_THREADS * end)));
		}
		threadPool.shutdown();

		threadPool.awaitTermination(1, TimeUnit.HOURS);
		System.out.println("Occurances in 5 threads: " + TextPart.getCountOccurances());
		System.out.println("5 threads time: " + (double) ((System.currentTimeMillis() - start5Threads) / 1000.0));

		
		System.out.println();
		System.out.println("=============== Finished program ===============");
		System.out.println();
		System.out.println("All time: " + (double) ((System.currentTimeMillis() - start) / 1000.0));
	}

	private static String readingFileExtractingString() {
		File file = new File("Books" + File.separator + "Lev_Tolstoj-Voina_i_mir.txt");
		InputStream is = null;
		

		try {
			is = new BufferedInputStream(new FileInputStream (file));
			int x;
			byte[] data = new byte[(int) file.length()];
			int index = 0;
			do {
				x = is.read();

				if (x != -1) {
					data[index++] = (byte) x;
				}
			} while (x != -1);

			String text = new String(data, "UTF-8");
			return text;
		} catch (IOException e) {
			return "";
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static int countOccurrences(String text, char whatToCount) {


		//long count = text.chars().filter(ch -> ch == whatToCount).count();
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == whatToCount) {
				count++;
			}
		}
		return count;
	}
}
