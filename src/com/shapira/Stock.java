package com.shapira;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.java_cup.internal.runtime.Symbol;

public class Stock {

	private String symbole;
	private float ask;
	private float bid;
	private Date date;
	
	public Stock(String symbol, float ask, float bid, Date date) {
		setSymbole(symbol);
		setAsk(ask);
		setBid(bid);
			this.date = date;
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

	
