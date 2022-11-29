package bansalsi_CSCI201_Assignment2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.validator.GenericValidator;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class JsonProcessor
{
	JsonProcessor(){
		allCompTickers = new HashSet<String>();
		tickers = new HashSet<String>();
		tickers.clear();
		allCompTickers.clear();
	}
	
	public static JsonObject getJsonData()
	{
		
		Gson gson = new Gson();
		JsonObject jObj = new JsonObject();
	
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		String fileName = new String();
		
		Reader file;
		
		while(true)
		{
			System.out.println("What is the name of the file containing the company information?");
			fileName = scan.nextLine();
			try
			{
				file = new FileReader(fileName);
			}
			catch (FileNotFoundException FFE)
			{
				System.out.println("File Not Found, Enter Another File!");
				continue;
			}
			break;
		}		
		
		
		jObj.works = true;
		try
		{
			jObj = gson.fromJson(file, JsonObject.class);
			file.close();
			
		}
		catch(NullPointerException NPE)
		{
			return getJsonData();
		}
		catch (JsonSyntaxException JSE)
		{
			System.out.println("The file " + fileName + " is not formatted correctly\n");
			jObj.works = false;
			return jObj;
		}
		catch (JsonIOException JIOE)
		{
			System.out.println("The file " + fileName + " is not formatted correctly\n");
			jObj.works = false;
			return jObj;
		}
		catch (IOException IOE)
		{
			System.out.println("The file " + fileName + " is not formatted correctly\n");
			jObj.works = false;
			return jObj;
		}
		
		if(jObj == null)
		{
			System.out.println("The file " + fileName + "does not have any data\n");
			jObj = new JsonObject();
			jObj.works = false;
			return jObj;
		}
	
	
	for(Company x : jObj.data)
	{
		if(verifyEntry(x) == -1)
		{
			return getJsonData();
		}
	}
		System.out.println("The file has been properly read.");
		jObj.filename = fileName;
		
		return jObj;
	}

	private static int verifyEntry(Company comp)
	{
		if(allCompTickers.contains(comp.ticker))
		{
			System.out.println("Ticker already exists");
			return -1;
		}
		
		if(!(comp.exchangeCode.equalsIgnoreCase("NASDAQ") || comp.exchangeCode.equalsIgnoreCase("NYSE")))
		{
			System.out.println("Invalid stock exchange listed in Json file");
			return -1;
		}
		
		if(!checkValidDate(comp.startDate))
		{
			System.out.println("Invalid date format");
			return -1;
		}
		
		if(comp.description.isEmpty())
		{
			System.out.println("No Description, invalid format");
			return -1;
		}
		if(comp.name.isEmpty())
		{
			System.out.println("No Company name, invalid format");
			return -1;
		}
		if(comp.ticker.isEmpty())
		{
			System.out.println("No Company ticker, invalid format");
			return -1;
		}
		if(comp.startDate.isEmpty())
		{
			System.out.println("No Start Date, invalid format");
			return -1;
		}
		
		allCompTickers.add(comp.ticker);
		tickers.add(comp.exchangeCode);
		
		
		return 0;
	}
	
	private static boolean checkValidDate(String date)
	{
		
		return GenericValidator.isDate(date, "yyyy-mm-dd", true);
		
	}
	
	@SuppressWarnings("unused")
	private static BufferedReader getJsonFile()
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("data: ");
		String fileName = scan.next();
		scan.close();
		return fileValidator(fileName);
	}
	
	private static BufferedReader fileValidator(String source)
	{
		BufferedReader inputFile = null;
		try
		{
			inputFile = new BufferedReader(new FileReader(source));
		} catch (IOException except)
		{
			System.out.println("The file " + source + " could not be found\n");
			
		} 
		return inputFile;
	}	

	public static boolean nameValidator(JsonObject jObj, String str)
	{
		for(Company x : jObj.data)
		{
			if(x.name.equalsIgnoreCase(str))
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean tickerValidator(JsonObject jObj, String str)
	{
		for(Company x : jObj.data)
		{
			if(x.ticker.equalsIgnoreCase(str))
			{
				return false;
			}
		}
		return true;
	}

	public static boolean dateValidator(JsonObject jObj, String str)
	{
		return checkValidDate(str);
	}

	public static boolean xChangeValidator(String str)
	{
		return str.equalsIgnoreCase("NASDAQ") || str.equalsIgnoreCase("NYSE");
	}
	
	static Set<String> allCompTickers;
	static Set<String> tickers;
}