package lab5;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.lang.Math;



public class RandomNumberGenerator {

	RandomNumberGenerator()
	{
		Random rand = new Random();
		randomNumbers = new ArrayList<Integer>();
		for(int i = 0; i < 100000000; ++i)
		{
			randomNumbers.add(Math.abs(rand.nextInt()%100000000));
		}
		
		notInArrayTarget = 100000001;
		target = randomNumbers.get(Math.abs((rand.nextInt()%100000000)));
	}
	
	public static ArrayList<Integer> randomNumbers;
	public static Integer target;
	public static int notInArrayTarget;
}


class Sequential
{
	void Run(boolean test)
	{
		if(test) {
		int i = 0;
		for(Integer x : RandomNumberGenerator.randomNumbers)
		{
			if(x == RandomNumberGenerator.target)
			{
				System.out.println("Found number at index " + i + " at time " +
						(System.currentTimeMillis()-Main.start));
			}
			++i;
		}
		}
		else
		{
		int i = 0;
		Main.start = System.currentTimeMillis();
		for(Integer x : RandomNumberGenerator.randomNumbers)
		{
			if(x == RandomNumberGenerator.notInArrayTarget)
			{
			}
			++i;
		}
		System.out.println(""
				+ "Target Number," + RandomNumberGenerator.notInArrayTarget + " not found in array, time taken " + 
				(System.currentTimeMillis()-Main.start));
		}
	}
}

class multiThread extends Thread
{
	public multiThread() {}
	
	public multiThread(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public multiThread(int x, int y, boolean test)
	{
		this.x = x;
		this.y = y;
		this.test = test;
	}
	
	public void runThisClass(boolean Test)
	{
		if(Test)
		{
		Thread t1 = new multiThread(0, 24999999);
		Thread t2 = new multiThread(24999999,49999999);
		Thread t3 = new multiThread(50000000,74999999);
		Thread t4 = new multiThread(75000000, 100000000);
		
		ExecutorService ES = Executors.newFixedThreadPool(4);
		
		ES.submit(t1);
		ES.submit(t2);
		ES.submit(t3);
		ES.submit(t4);
		
		ES.shutdown();
		
		while(!ES.isTerminated())
		{
			Thread.yield();
		}
		}
		
		else
		{
		
	
			Main.start = System.currentTimeMillis();
			Thread t1 = new multiThread(0, 24999999, false);
			Thread t2 = new multiThread(24999999,49999999, false);
			Thread t3 = new multiThread(50000000,74999999, false);
			Thread t4 = new multiThread(75000000, 100000000, false);
			ExecutorService BS = Executors.newFixedThreadPool(4);
			
			BS.submit(t1);
			BS.submit(t2);
			BS.submit(t3);
			BS.submit(t4);
			BS.shutdown();
			while(!BS.isTerminated())
			{
				Thread.yield();
			}
			
			try {

				t1.join();
				t2.join();
				t3.join();
				t4.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(""
				+ "Target Number," + RandomNumberGenerator.notInArrayTarget + " not found in array, time taken " + 
				(System.currentTimeMillis()-Main.start));
		}
		
		
	}
	
	public void run()
	{
		if(test == false)
		{
			notInArr();
		}
		else 
		{
			inArr();
		}
	}
	
	public void inArr()
	{
		int i = x;
		while (i <=y && (!found))
		{
			if(RandomNumberGenerator.target == RandomNumberGenerator.randomNumbers.get(i))
			{
				System.out.println("Found number at index " + i + " Total time taken " +
									(System.currentTimeMillis() - Main.start));
				found = true;
				return;
			}
			++i;
		}
	}
	
	public void notInArr()
	{
		int i = x;
		while (i <=y && (!found))
		{
			if(RandomNumberGenerator.notInArrayTarget == RandomNumberGenerator.randomNumbers.get(i))
			{
				System.out.println("Found number at index " + i + " Total time taken " +
									(System.currentTimeMillis() - Main.start));
				found = true;
				return;
			}
			++i;
		}
	}
	
	int x;
	int y;
	static boolean found = false;
	boolean test = true;
}

class multiProcess extends RecursiveAction
{
	multiProcess(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	multiProcess(int x, int y, boolean test)
	{
		this.x = x;
		this.y = y;
		this.test = test;
	}

	@Override
	protected void compute() {
		if(test == false)
		{
			notInArr();
		}
		else 
		{
			inArr();
		}
	}
	
	public void inArr()
	{
		int i = x;
		while (i <=y && (!found))
		{
			if(RandomNumberGenerator.target == RandomNumberGenerator.randomNumbers.get(i))
			{
				System.out.println("Found number at index " + i + " Total time taken " +
									(System.currentTimeMillis() - Main.start));
				found = true;
				return;
			}
			++i;
		}
	}
	
	public void notInArr()
	{
		int i = x;
		while (i <=y && (!found))
		{
			if(RandomNumberGenerator.notInArrayTarget == RandomNumberGenerator.randomNumbers.get(i))
			{
				System.out.println("Found number at index " + i + " Total time taken " +
									(System.currentTimeMillis() - Main.start));
				found = true;
				return;
			}
			++i;
		}
	}
	
	public void runThisClass(boolean test)
	{
		if(test) {
		RecursiveAction t1 = new multiProcess(0, 24999999);
		RecursiveAction t2 = new multiProcess(24999999,49999999);
		RecursiveAction t3 = new multiProcess(50000000,74999999);
		RecursiveAction t4 = new multiProcess(75000000, 100000000);
		
		ForkJoinPool ES = new ForkJoinPool();
		
		
		ES.execute(t1);
		ES.execute(t2);
		ES.execute(t3);
		ES.execute(t4);
		
		ES.shutdown();
		
		while(!ES.isTerminated())
		{
			Thread.yield();
		}
		
		}else
		{
		Main.start = System.currentTimeMillis();
		multiProcess t1 = new multiProcess(0, 24999999, false);
		multiProcess t2 = new multiProcess(24999999,49999999, false);
		multiProcess t3 = new multiProcess(50000000,74999999, false);
		multiProcess t4 = new multiProcess(75000000, 100000000, false);
		ForkJoinPool BS = new ForkJoinPool();
		
		BS.execute(t1);
		BS.execute(t2);
		BS.execute(t3);
		BS.execute(t4);
		BS.shutdown();
		
		while(!BS.isTerminated())
		{
			Thread.yield();
		}
		
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		
		System.out.println(""
				+ "Target Number," + RandomNumberGenerator.notInArrayTarget + " not found in array, time taken " + 
				(System.currentTimeMillis()-Main.start));
		}
		
		
	}
	
	int x;
	int y;
	static boolean test = true;
	static boolean found;
}





