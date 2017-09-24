package treads;

public class FinderThread implements Runnable {
private String text;

	public FinderThread(String text) {
	this.text = text;
}
	@Override
	public void run() {
		long occurances = this.text.chars()
				.filter(ch -> ch ==',')
				.count();
		System.out.println("Occurances in one thread: " + occurances);
		
	}

}
