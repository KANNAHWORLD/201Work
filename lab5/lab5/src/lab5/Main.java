package lab5;

public class Main {

	public static void main(String [] args)
	{
		//just to initialize the Random Number Generator
		//Only has static methods so it is fine not to assign it anything
		new RandomNumberGenerator();
		Sequential search = new Sequential();
		start = System.currentTimeMillis();
		System.out.println("Sequential: number in array");
		search.Run(true);
		System.out.println("\n\nSequential: number not in array");
		search.Run(false);
		
		
	
		multiThread MT = new multiThread();
		start = System.currentTimeMillis();
		System.out.println("\n\nMultiThread number in array:");
		MT.runThisClass(true);
		System.out.println("\n\nMultiThread number not in array: ");
		MT.runThisClass(false);
		
		
		multiProcess MP = new multiProcess(0,0);
		
		start = System.currentTimeMillis();

		System.out.println("\n\nMultiProcess in array:");
		MP.runThisClass(true);
		
		System.out.println("\n\nMultiProcess not in array:");
		MP.runThisClass(false);
		
		
	}
	
	static long start;
	
}
