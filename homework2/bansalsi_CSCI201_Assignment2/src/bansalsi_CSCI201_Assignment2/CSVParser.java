package bansalsi_CSCI201_Assignment2;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class CSVParser {
	public static ArrayList<CSVContents> getTextFile()
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		FileReader file;
		Scanner input;
		parsedData = new ArrayList<CSVContents>();
		while(true)
		{
			try 
			{
				System.out.println("What is the name of the file containing the schedule information?");
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
			parsedData.add(new CSVContents(Integer.parseInt(split[0]), 
											split[1], 
											Integer.parseInt(split[2]), 
											Integer.parseInt(split[3])
											));
		}
		input.close();
		return parsedData;
	}
	
	public static ArrayList<CSVContents> parsedData;
}

class CSVContents
{
	CSVContents(int time, String ticker, int tradeamount, int quote)
	{
		this.time = time;
		this.ticker = ticker;
		this.tradeamount = tradeamount;
		this.quote = quote;
	}
	
	int time;
	String ticker;
	int tradeamount;
	int quote;
}