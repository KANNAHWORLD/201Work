package bansalsi_CSCI201_Assignment3;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.*;
import java.util.*;


import java.util.concurrent.*;
import com.google.common.collect.*;
import com.google.gson.Gson;

public class Server {
	public Vector<ServerThread> clients;
	ArrayList<CSVSchedule> schedule;
	ArrayList<CSVTrader> traders;
	public static HashMap<String, Company> priceMap;
	public static int currentJobTest = 0;
	public static Lock jobLock = new ReentrantLock();
	public static PeekingIterator<CSVSchedule> PeekingJobIt;
	public static ArrayList<CSVSchedule> unDoneTrades = new ArrayList<CSVSchedule>();
	public static int amountTraders;
	public static int jobAmount;
	
	public void startServer(int port)
	{
		clients = new Vector<ServerThread>();
		this.port = port;
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Port not usable.");
			System.exit(0);
		}
		
		schedule = CSVParser.getTextFile("Schedule");
		traders = CSVTraderParser.getTextFile("traders");
		priceMap = JsonProcessor.getPriceData(schedule);
		ExecutorService executor = Executors.newCachedThreadPool();
		
		System.out.println("Listiening on port " + port + ".\nWaiting for traders...");

		for(int i = 0; i < traders.size(); ++i)
		{
			Socket s = null;
			try {
				s = server.accept();
			} catch (IOException e) {
			}
			ServerThread st = new ServerThread(s, traders.size()-i-1, traders.get(i).startBalance, i+1);
			clients.add(st);
		
			System.out.println("Connection from: " + s.getInetAddress());
			if(i <  traders.size()-1)
			{
				System.out.println("Waiting for " + (traders.size()-i-1) + " more trader(s)...");
			}
		}
		TimeUtil.setStartTime();
		broadcast("All traders have arrived!");
		System.out.println("Starting service.");
		broadcast("Starting service.");
		broadcast("Next");

		int whichIndex = 0;
		
		PeekingJobIt = Iterators.peekingIterator(schedule.iterator());
		while(schedule.size() > 0)
		{
			while(schedule.size() > 0 && PeekingJobIt.peek().time <= TimeUtil.getSeconds())
			{
				int undone = 0;
				for(ServerThread x : clients)
				{
					if(x.canBuy(PeekingJobIt.peek()))
					{
						int count = 1;
						while(schedule.size() > 0  && PeekingJobIt.peek().time <= TimeUtil.getSeconds() && x.canBuy(PeekingJobIt.peek()))
						{
							undone = 0;
							x.assign(PeekingJobIt.peek());
							++count;
							schedule.remove(whichIndex);
							PeekingJobIt = Iterators.peekingIterator(schedule.iterator());
						}
						x.busyTime += count;
						x.whichTrade += 1;
						break;
					}
					else 
					{
						undone += 1;
					}
					
				}
				if(undone == clients.size())
				{
					unDoneTrades.add(PeekingJobIt.peek());
					schedule.remove(whichIndex);
					PeekingJobIt = Iterators.peekingIterator(schedule.iterator());
				}
				
			}
		}
		
		ExecutorService ex = Executors.newCachedThreadPool();
		
		
	}
	
	public static void main(String [] args)
	{
		Server s = new Server();
		s.startServer(3456);
		
	}
	
	public void broadcast(String s)
	{
		for (ServerThread x : clients)
		{
			x.printToSocket(s);
		}
	}
	
	int port;
}

class ServerThread extends Thread{
	public BufferedReader inputStream;
	public PrintStream outPutStream;
	public Socket socket;
	double initbalance;
	double balance;
	double profit = 0;
	int selfID = 0;
	int busyTime = 0;
	int whichTrade = 0;
	ArrayList<Integer> when = new ArrayList<Integer>();
	ArrayList<ArrayList<Job>> sending = new ArrayList<ArrayList<Job>>();
	
	public ServerThread(Socket s, int i, int balance, int selfID) {
		this.selfID = selfID;
		this.socket = s;
		this.balance = balance;
		this.initbalance = balance;
		
		for(int h = 0; h < 100; ++h)
		{
			sending.add(new ArrayList<Job>());
		}
		
		try {
			// to do --> store them somewhere, you will need them later 
			inputStream = new BufferedReader(new InputStreamReader(s.getInputStream()));
			outPutStream = new PrintStream(new BufferedOutputStream(s.getOutputStream()));
			
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
		this.initbalance = balance;
		if(i != 0)
		{
			outPutStream.println(i + " more trader is needed before the service can begin.");
			outPutStream.flush();
		}
		
	}

	public void printToSocket(String printString)
	{
		outPutStream.println(printString);
		outPutStream.flush();
	}
	
	public boolean canBuy(CSVSchedule c)
	{
		if(c.time < busyTime)
		{
			return false;
		}
		if(c.tradeamount < 0)
		{
			return true;
		}
		float neededfunds = c.tradeamount * Server.priceMap.get(c.ticker).current_price;
		if(neededfunds > balance)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void run()
	{
		buyAndSell();
	}
	
	public void assign(CSVSchedule c)
	{
		Job s = new Job(c.ticker, Server.priceMap.get(c.ticker).current_price, c.time, c.tradeamount);
		if(c.tradeamount < 0)
		{
			profit += (s.price * s.tdamt)*-1;
		}
		else
		{
			balance -= (s.price * s.tdamt);
		}
		sending.get(whichTrade).add(s);
	}
	
	public void buyAndSell()
	{
		
	}
}

class Job
{
	public Job() {}
	Job(String t, double p, int ttim, int tdamt)
	{
		this.tick = t;
		this.price = p;
		this.time = ttim;
		this.tdamt = tdamt;
	}
	public int tdamt;
	String tick;
	double price;
	int time;
}

