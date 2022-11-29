package bansalsi_CSCI201_Assignment2;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class JsonObject
{	
	JsonObject(){
		works = false;
		filename = new String();
		data = new ArrayList<Company>();

	}
	
	@SerializedName("data")
	public ArrayList<Company> data;
	
	boolean works;
	
	String filename;
}

class Company 
{
	public Company(){}
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("stockBrokers")
	public int stockBrokers;
	
	@SerializedName("ticker")
	public String ticker;
	
	@SerializedName("startDate")
	public String startDate;
	
	@SerializedName("description")
	public String description;
	
	@SerializedName("exchangeCode")
	public String exchangeCode;
}

