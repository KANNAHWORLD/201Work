package bansalsi_CSCI201_Assignment3;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class CSVParser {
	public static ArrayList<CSVSchedule> getTextFile(String fileType)
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		FileReader file;
		Scanner input;
		parsedData = new ArrayList<CSVSchedule>();
		while(true)
		{
			try 
			{
				System.out.println("What is the path of the " + fileType + " file?");
				file = new FileReader(scan.nextLine());
			} catch (FileNotFoundException e) {
				System.out.println("File not found, please enter a file name");
				continue;
			}
			break;
		}

		input = new Scanner(file);
		String[] split;
		while(input.hasNextLine())
		{
			split = input.nextLine().split(",");
			parsedData.add(new CSVSchedule(Integer.parseInt(split[0]), 
											split[1], 
											Integer.parseInt(split[2])
											));
		}
		input.close();
		
		System.out.println("The file has been properly read.\n");
		
		return parsedData;
	}
	
	public static ArrayList<CSVSchedule> parsedData;
}

class CSVSchedule
{
	CSVSchedule(int time, String ticker, int tradeamount)
	{
		this.time = time;
		this.ticker = ticker;
		this.tradeamount = tradeamount;
	}
	
	int time;
	String ticker;
	int tradeamount;
}

class CSVTraderParser {
	public static ArrayList<CSVTrader> getTextFile(String fileType)
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		FileReader file;
		Scanner input;
		parsedData = new ArrayList<CSVTrader>();
		while(true)
		{
			try 
			{
				System.out.println("What is the path of the " + fileType + " file?");
				file = new FileReader(scan.nextLine());
			} catch (FileNotFoundException e) {
				System.out.println("File not found, please enter a file name");
				continue;
			}
			break;
		}

		input = new Scanner(file);
		String[] split;
		while(input.hasNextLine())
		{
			split = input.nextLine().split(",");
			parsedData.add(new CSVTrader(Integer.parseInt(split[0]),
											Integer.parseInt(split[1])
											));
		}
		input.close();
		
		System.out.println("The file has been properly read.\n");
		
		return parsedData;
	}
	
	public static ArrayList<CSVTrader> parsedData;
}

class CSVTrader
{
	CSVTrader(int time, int startBalance)
	{
		this.time = time;
		this.startBalance = startBalance;
	}
	
	int time;
	int startBalance;
}