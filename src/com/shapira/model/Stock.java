package com.shapira.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.algo.model.StockInterface;

import com.shapira.model.Portfolio.ALGO_RECOMMENDATION;

public class Stock implements StockInterface{
	
	private float balance;
	private String symbol;
	private float ask;
	private float bid;
	private Date date;
	private ALGO_RECOMMENDATION recommendation;
	private int stockQuantity;

public Stock()
{
	symbol = null;
	ask = 0;
	bid = 0;
	this.date = null;
	stockQuantity = 0;
}

public Stock(StockInterface stockInterface) {
	this.symbol = new String(stockInterface.getSymbol());
	this.ask = stockInterface.getAsk();
	this.bid = stockInterface.getBid();
	new Date(stockInterface.getDate().getTime());
	this.recommendation = ((Stock) stockInterface).getRecommendation();
	this.stockQuantity = ((Stock) stockInterface).getStockQuantity();
}

	public void updateStockBalance(float amount){
		if (getBalance() + amount >= 0){
			setBalance(getBalance() + amount);
		}
		else
			System.out.println("Negative Balance!");
	}
	
	public ALGO_RECOMMENDATION getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
		this.recommendation = recommendation;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	private void setBalance(float balance) {
		this.balance = balance;
	}

	public float getBalance() {
		return balance;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public float getAsk() {
		return ask;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}

	public float getBid() {
		return bid;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHtmlDescription() {
		SimpleDateFormat simpleDF = new SimpleDateFormat("MM/dd/yyyy");
		return "<b>Stock symbol: </b>" +getSymbol()+", <b>ask: </b>" + getAsk() + ", <b>bid: </b>" + getBid() + "<b> date: </b>" + simpleDF.format(getDate());
	}

}



