package com.shapira.model;


public class portfolio {
	private String title;
	private static final int MAX_PORTFOLIO_SIZE = 5;
	private Stock[] stocks = new Stock[MAX_PORTFOLIO_SIZE];
	private int stockIndex = 0;

	//c'tor
	public portfolio() {
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.title = new String("");	
		this.stockIndex = 0;
	}
	//copy c'tor
	 	public portfolio(portfolio portfolio){
		this.title = new String(portfolio.getTitle());
		this.stockIndex = portfolio.getStockIndex();
		
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		for (int i = 0; i < this.stockIndex; i++)
		{
			this.stocks[i] = new Stock(portfolio.getStocks()[i]);
		}
	}
	 	//Add Stock
	public void addStock(Stock stock) 
	{

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
	
	//Remove Stock
	public void removeStock(String symbol)
	{
		if (symbol == null) return;

		else if (stockIndex == 1 || symbol.equals(stocks[stockIndex-1].getSymbole()))
		{
			stocks[stockIndex-1] = null;
			stockIndex--;
			return;
		}
		for (int i = 0; i < stockIndex; i++)
		{

			if (symbol.equals(stocks[i].getSymbole()))
			{
				stocks[i] = stocks[stockIndex - 1];
				stocks[stockIndex - 1] = null;
				stockIndex--;
			}

		}
		return;
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

	public int getStockIndex() {
		return stockIndex;
	}

	public void setStockIndex(int stockIndex) {
		this.stockIndex = stockIndex;
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
