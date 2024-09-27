package modelsJson;

import java.util.HashMap;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Info{

	@JsonProperty("userID")
	private String userID;

	@JsonProperty("tickers")
	private HashMap<String, TickersItem> tickers;

	public void setUserID(String userID){
		this.userID = userID;
	}

	public String getUserID(){
		return userID;
	}

	public void setTickers(HashMap<String,TickersItem> tickers){
		this.tickers = tickers;
	}

	public HashMap<String,TickersItem> getTickers(){
		return tickers;
	}
}