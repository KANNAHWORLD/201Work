package bansalsi_CSCI201_Assignment2;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Brokers
{
	Brokers(){}
	
	public static Map<String, Semaphore> CompanyBrokerCount;
	public static ArrayList<CSVContents> JobList;
	public static synchronized void print(String x)
	{
		System.out.println(x);
	}
	
}


class Job implements Runnable
{
	private static Lock printLock = new ReentrantLock();
	
	private CSVContents csvObj;
	
	Job(CSVContents csvObj)
	{
		this.csvObj = csvObj;
	}
	
	public void run()
	{
		if(csvObj.tradeamount < 0)
		{
			sell();
		}
		else
		{
			buy();
		}
	}
	
	public void sell()
	{
		while(Util.getSeconds() < csvObj.time)
		{
		}

		Brokers.CompanyBrokerCount.get(csvObj.ticker).acquireUninterruptibly();
		
		printLock.lock();
		System.out.println("["+Util.getTime()+"]" + " Starting sale of " + -1*csvObj.tradeamount + " stocks of " + csvObj.ticker);
		printLock.unlock();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Main.tempBalance(-1*csvObj.quote * csvObj.tradeamount);
		printLock.lock();
		System.out.println("["+Util.getTime()+"]" + " Finished sale of " + -1*csvObj.tradeamount + " stocks of " + csvObj.ticker
		+"\nCurrent balance after trade: " + Main.updateBalance(-1*csvObj.quote * csvObj.tradeamount));
		printLock.unlock();
//		System.out.println("Current balance after trade: " + Main.updateBalance(-1*csvObj.quote * csvObj.tradeamount));
		Brokers.CompanyBrokerCount.get(csvObj.ticker).release();
	}
	
	public void buy()
	{
		// add a thing to check if it is time to process the thing
		while(Util.getSeconds() < csvObj.time)
		{
		}

		Brokers.CompanyBrokerCount.get(csvObj.ticker).acquireUninterruptibly();
		
		printLock.lock();
		System.out.println("["+Util.getTime()+"]" + " Starting purchase of " + csvObj.tradeamount + " stocks of " + csvObj.ticker);
		printLock.unlock();
		
		// If the balance is less than purchase amount, do this
		int balance = Main.tempBalance(-1 * csvObj.quote * csvObj.tradeamount);
		
		if(balance == -1)
		{
			System.out.println("Transaction failed due to insufficient balance. Unsuccessful purchase\nof "
					+ csvObj.tradeamount + " stocks of " + csvObj.ticker);
			return;
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		printLock.lock();
		System.out.println("["+Util.getTime()+"]" + " Finished purchased of " + csvObj.tradeamount + " stocks of " + csvObj.ticker
		+"\nCurrent balance after trade: " + (Main.updateBalance(-1 * csvObj.quote * csvObj.tradeamount)));
		printLock.unlock();
		
		Brokers.CompanyBrokerCount.get(csvObj.ticker).release();
	}
	
	
}