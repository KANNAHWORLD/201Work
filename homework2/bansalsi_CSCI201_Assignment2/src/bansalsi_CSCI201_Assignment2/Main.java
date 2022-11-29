package bansalsi_CSCI201_Assignment2;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


// NEED TO FIX BAD FILE CONTENXT


public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String [] args) {
		
		//startTime = System.currentTimeMillis();
//		System.out.println(Util.getTime());
		//Initializing
		new JsonProcessor();
		Brokers.CompanyBrokerCount = new HashMap<String, Semaphore>();
		ExecutorService executer = Executors.newCachedThreadPool();
		
		
		//Obtaining Data
		JsonObject jsonData = JsonProcessor.getJsonData();
		
		for(Company x : jsonData.data)
		{
			Brokers.CompanyBrokerCount.put(x.ticker, new Semaphore(x.stockBrokers));
		}
		
		Brokers.JobList = CSVParser.getTextFile();
		Main.getStartingBalance();

		System.out.println("\nInitial Balance: " + Main.balance);
//	
		startTime = System.currentTimeMillis();
		for ( CSVContents x : Brokers.JobList)
		{
			executer.submit(new Job(x));
		}

		startTime = System.currentTimeMillis();
		executer.shutdown();
		
		
		
		while(!executer.isTerminated())
		{
			Thread.yield();
		}
		
		System.out.println("All trades completed!");
		
	}
	
	public static synchronized int updateBalance(int update)
	{
		if ((balance + update) < 0)
		{
			return -1;
		}
		
		balance += update;
		return balance;
	}
	
	public static synchronized int tempBalance(int update)
	{
		if ((temporaryBalance + update) < 0)
		{
			return -1;
		}
		else
		{
			temporaryBalance += update;
			return temporaryBalance;
		}
	}
	
	
	public static void getStartingBalance()
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("What is the Initial Balance?");
			try
			{
				balance = Integer.parseInt(scan.nextLine());
			}
			catch(NumberFormatException NFE)
			{
				System.out.println("Please enter a valid number");
				continue;
			}
			if(balance < 0)
			{
				System.out.println("Invalid balance");
				continue;
			}
			temporaryBalance = balance;
			break;
		}
	}
	public static int balance;
	public static int temporaryBalance;

	public static long startTime;
}
