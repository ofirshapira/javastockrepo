package com.shapira.model;

import com.shapira.Stock;

public class portfolio {
	private String title;
	private static final int MAX_PORTFOLIO_SIZE = 5;
	private Stock[] stocks = new Stock[MAX_PORTFOLIO_SIZE];
	private int stockIndex = 0;

	public void addStock(Stock stock) {
		
		if(stock != null && stockIndex  < MAX_PORTFOLIO_SIZE)
		{
			this.stocks[stockIndex] = stock;
			stockIndex++;
		}
		else
		{
			System.out.println("Error! -> portfolio is full OR Stock is null!");
		}
	}
	
	 	public String getTitle()
	 	{
		return title;
	 	}

	 	public void setTitle(String title)
	 	{
		this.title = title;
	 	}

	 	public Stock[] getStocks()
	 	{
		return stocks;
	 	}


		public String getHtmlString(){
	 		 String result = new String();
	 		 result = "<h1>" + getTitle() + "</h1>";
	 		 
	  		for (int i = 0; i < stockIndex; i++)
	  		{
	  			Stock presentStock = stocks[i];
	  			result+= presentStock.getHtmlDescription() + "<br>";
	  		}
	  		return result; 
	 	}
		
		
	
}
