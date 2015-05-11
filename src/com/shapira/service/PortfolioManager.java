package com.shapira.service;

import java.util.Date;
import java.util.Calendar;

import com.shapira.model.Stock;
import com.shapira.model.portfolio;

public class PortfolioManager {

	public portfolio getPortfolio(){
		
		portfolio myportfolio  = new portfolio();
		myportfolio.setTitle("Exercise 7 portfolio");
		myportfolio.updateBalance(10000);
		
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 15);
		Date date = cal.getTime();
	
		
		Stock pih = new Stock("PIH", 10.0f, 8.5f , date);
		myportfolio.addStock(pih);
		Stock aal = new Stock("AAL", 30.0f, 25.5f ,date);
		myportfolio.addStock(aal);
		Stock caas = new Stock("CAAS", 20.0f, 15.5f ,date);
		myportfolio.addStock(caas);
		
		myportfolio.buyStock(pih,20);
		myportfolio.buyStock(aal,30);
		myportfolio.buyStock(caas,40);
		
		myportfolio.sellStock("AAL",-1);
		myportfolio.removeStock("CAAS");
		
		return myportfolio;
		
	}
	

	
}
