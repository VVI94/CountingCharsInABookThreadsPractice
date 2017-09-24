package treads;

public class TextPart implements Runnable {

	private String partText="";
	private String partName = "Part";
	private static int count = 1;
	private static int countOccurances = 0;
	
	public TextPart(String partText) {
		this.partText = partText;
		this.partName = partName + count;
		count++;
	}

	@Override
	public void run() {
		
		int count = 0;
	    for (int i=0; i < partText.length(); i++)
	    {
	        if (partText.charAt(i) == ',')
	        {
	             count++;
	        }
	    }
	   System.out.println(partName + ": " + count);
	   
		//long occurances = partText.chars()
		//						.filter(ch -> ch ==',')
		//						.count();
		
		//System.out.println(occurances);
		countOccurances += count;
		
	}

	public static int getCountOccurances() {
		return countOccurances;
	}

	
}
