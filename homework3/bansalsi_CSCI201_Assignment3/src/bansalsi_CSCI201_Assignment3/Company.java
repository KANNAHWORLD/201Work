package bansalsi_CSCI201_Assignment3;

import com.google.gson.annotations.SerializedName;

public class Company 
{
	@SerializedName("c")
	public float current_price;
	
	@SerializedName("d")
	public double change;
	
	@SerializedName("dp")
	public double percent_change;
	
	@SerializedName("h")
	public double high_price;
	
	@SerializedName("l")
	public double low_price;
	
	@SerializedName("o")
	public double open_price;

	@SerializedName("pc")
	public double previous_close;
	
	@SerializedName("t")
	public double trading_day;
}

