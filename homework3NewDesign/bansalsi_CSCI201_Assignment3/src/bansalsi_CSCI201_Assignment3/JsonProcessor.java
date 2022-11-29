package bansalsi_CSCI201_Assignment3;

import java.util.ArrayList;



import java.util.HashMap;
import java.util.HashSet;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.*;

import com.google.gson.Gson;

public class JsonProcessor
{	
	public static HashMap<String, Company> priceMap = new HashMap<String, Company>();
	
	private static final String APIKey = "cd8qsdaad3i7j6r2feogcd8qsdaad3i7j6r2fep0";
	private static final String URL = "https://finnhub.io/api/v1/quote?";
	private static HashSet<String> check = new HashSet<String>();
	
	public static HashMap<String, Company> getPriceData(ArrayList<CSVSchedule> all)
	{
		Gson gson = new Gson();
		@SuppressWarnings("unused")
		HttpResponse<String> response = null;
		
		for(CSVSchedule x : all)
		{
			if(check.contains(x.ticker))
			{
				continue;
			}
			else
			{
				check.add(x.ticker);
			}
		
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(getQuoteURL(x.ticker)))
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Company cp = gson.fromJson(response.body(), Company.class);
//			System.out.println(response.body());
			priceMap.put(x.ticker, cp);
		}
		return priceMap;
	}
	
	
	public static String getQuoteURL(String ticker)
	{
		String ret = new String();
		
		for(int i = 0; i < URL.length(); ++i)
		{
			ret += URL.charAt(i);
		}
		
		ret += "symbol=" + ticker.toUpperCase() + "&" + "token=" + APIKey;
		
//		"https://finnhub.io/api/v1/quote?" + "symbol=" + ticker + "&" + "token=" + APIKey
		
		return ret;
	}

	
}
