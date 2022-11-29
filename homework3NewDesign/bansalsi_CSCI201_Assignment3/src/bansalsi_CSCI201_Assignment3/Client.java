package bansalsi_CSCI201_Assignment3;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService.*;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

public class Client{
	
	public static long startTime = 0;
	
	public void createClient()
	{
		System.out.println("Welcome to SalStocks v2.0! \nEnter the server hostname:");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		Socket sock = null;
		BufferedReader br;
		PrintStream out;
		while (true)
		{
			try 
			{
				String serverPlace = scan.nextLine();
				System.out.println("Enter the server port");
				int port = scan.nextInt();
				sock = new Socket(serverPlace, port);
				br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new PrintStream(new BufferedOutputStream(sock.getOutputStream()));
			}
			catch(Exception e)
			{
				System.out.println("There was an error with the IP address and port"
						+ "please try again");
				continue;
			}
			break;
		}
		String action = "  ";
		try {
			System.out.println("Here1");
			action = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("Waiting...");
		String string = "Next";
		//This section is waiting for the other traders to join
		while (!action.equals(string))
		{
			System.out.println("Here2");
			try 
			{
				System.out.println(action);
				action = br.readLine();
			} catch (IOException e) {
				System.out.println("Println error");
			}
		}
		Gson gson = new Gson();
		try {
			System.out.println("Here5");
			action = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println("Here6");
		TimeUtil.setStartTime();
		ExecutorService ex = Executors.newCachedThreadPool();
		
		String end = "End";
		
		while(!action.equals(end))
		{
			System.out.println("Here3");
			currentJobs = gson.fromJson(action, sObj.class);
			System.out.println(action);
			for(Job x : currentJobs.list)
			{
				ex.execute(new JobTrader(x));
			}
			try {
				action = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ex.shutdown();
		while(!ex.isTerminated())
		{
			System.out.println("Here4");
		}
		out.println("Done");
		out.flush();
		try {
			action = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String none = "None";
		System.out.print("[" + TimeUtil.getTime() + "]" + " Incomplete Trades: ");
		if(action.equals(none))
		{
			System.out.println(none);
		}
		else
		{
			sObj printUnDone = gson.fromJson(action, sObj.class);
			
			for(Job x : printUnDone.list)
			{
				System.out.println("(" + x.time + ", " + x.tick + ", " + x.tdamt + ", " + TimeUtil.getTimeAss3() + ")");
			}
		}
		
		try {
			action = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Total Profit Earned: $" + action + ".");
		System.out.println("\nProcessing complete.");
	}
	
	sObj currentJobs = new sObj();
	
	public static void main(String [] args)
	{
		Client c = new Client();
		c.createClient();
	}
	
}

class sObj
{
	public ArrayList<Job> list;
	
	public sObj()
	{}
}


class JobTrader extends Thread
{
	Job job;
	
	public JobTrader(){}
	
	JobTrader(Job job)
	{
		this.job = job;
	}
	
	public void run()
	{
		if(job.tdamt < 1)
		{
			job.tdamt = -1*job.tdamt;
			sell();
		}
		else
		{
			buy();
		}
	}
	
	public void buy()
	{
		while(job.time > TimeUtil.getSeconds())
		{
			continue;
		}
		System.out.println("[" + TimeUtil.getTime() + "] Assigned purchase of " + job.tdamt + " stock(s) of " + job.tick
				+ ". Total cost estimate = " + job.price + "*" + job.tdamt + " = " + job.price * job.tdamt);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[" + TimeUtil.getTime() + "] Finished purchase of " + job.tdamt + " stock(s) of " + job.tick
				+ ".");
	}
	
	public void sell()
	{
		while(job.time > TimeUtil.getSeconds())
		{
			continue;
		}
		System.out.println("[" + TimeUtil.getTime() + "] Assigned sale of " + job.tdamt + " stock(s) of " + job.tick
				+ ". Total gain estimate = " + job.price + "*" + job.tdamt + " = " + job.price * job.tdamt);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[" + TimeUtil.getTime() + "] Finished sale of " + job.tdamt + " stock(s) of " + job.tick
				+ ".");
	}
}
