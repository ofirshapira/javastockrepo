package com.shapira.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.java_cup.internal.runtime.Symbol;


public class Stock {

	public enum ALGO_RECOMMENDATION{
		BUY, 
		SELL,
		REMOVE,
		HOLD,
	}
	ALGO_RECOMMENDATION HOLD;
	private String symbole;
	private float ask;
	private float bid;
	private Date date;
	private ALGO_RECOMMENDATION recommendation;
	private int stockQuantity;


	public Stock(String symbol, float ask, float bid, Date date) {
		setSymbole(symbol);
		setAsk(ask);
		setBid(bid);
		this.date = date;
		this.stockQuantity = 0;
	}


	public Stock(Stock stock){
		this(new String(stock.getSymbole()), stock.getAsk(), stock.getBid(), new Date(stock.getDate().getTime()));

		//		this.symbole = stock.getSymbole();
		//		this.ask = stock.getAsk();
		//		this.bid = stock.getBid();
		//		this.date = stock.getDate();
		this.stockQuantity = stock.getStockQuantity();
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
	
	public String getSymbole() {
		return symbole;
	}

	public void setSymbole(String symbole) {
		this.symbole = symbole;
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
		SimpleDateFormat simple = new SimpleDateFormat("MM/dd/yyyy");
		return "<b>Stock symbol: </b>" +getSymbole()+", <b>ask: </b>" + getAsk() + ", <b>bid: </b>" + getBid() + "<b> date: </b>" + simple.format(getDate());
	}

}



