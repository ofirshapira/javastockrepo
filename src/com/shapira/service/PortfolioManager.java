package com.shapira.service;

import java.util.Calendar;
import java.util.Date;

import com.shapira.Stock;
import com.shapira.model.portfolio;

public class PortfolioManager {

	public portfolio getPortfolio(){
		portfolio portfolio  = new portfolio();
		portfolio.setTitle("Shapira's portfolio");
		
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 10, 15);
		Date date1 = cal.getTime();
		Date date2 = cal.getTime();
		Date date3 = cal.getTime();
		
		Stock pih = new Stock("PIH", 13.1f,12.4f , date1);
		portfolio.addStock(pih);
		Stock aal = new Stock("AAL", 5.78f,5.5f ,date2);
		portfolio.addStock(aal);
		Stock caas = new Stock("CAAS", 32.2f,31.5f ,date3);
		portfolio.addStock(caas);
		
		
		
		return portfolio;
		
	}
}
