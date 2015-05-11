package com.shapira.model;

import java.text.Bidi;


public class portfolio {
	private String title;
	private static final int MAX_PORTFOLIO_SIZE = 5;
	private Stock[] stocks = new Stock[MAX_PORTFOLIO_SIZE];
	private int sizeOfPortfolio = 0;
	private float balance;
	private int isStockExist = -1;


	//c'tor
	public portfolio() {
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.title = new String("");	
		this.sizeOfPortfolio = 0;
		this.balance = 0;
	}
	//copy c'tor
	public portfolio(portfolio portfolio){
		this.title = new String(portfolio.getTitle());
		this.sizeOfPortfolio = portfolio.getStockIndex();

		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		for (int i = 0; i < this.sizeOfPortfolio; i++)
		{
			this.stocks[i] = new Stock(portfolio.getStocks()[i]);
		}
		this.balance = portfolio.getBalance();
	}


	//Add Stock + validation 
	public void addStock(Stock stock) 
	{
		if ( sizeOfPortfolio >= MAX_PORTFOLIO_SIZE){
			System.out.println("Can't add new stock, portfolio can have only" + MAX_PORTFOLIO_SIZE + "stocks");

			return;
		}

		for (int i = 0; i < sizeOfPortfolio; i++){
			if (stock.getSymbole().equals(stocks[i].getSymbole())){
				isStockExist = i;
				System.out.println("Stock Exists in index "+isStockExist+" in portfolio");
				return;
			}
		}

		if(stock != null && sizeOfPortfolio  < MAX_PORTFOLIO_SIZE && isStockExist == -1 )
		{
			this.stocks[sizeOfPortfolio] = stock;
			sizeOfPortfolio++;
		}
		else
		{
			System.out.println("Error! -> portfolio is full OR Stock is null!");
		}
	}

	//Sell Stock
	public boolean sellStock (String symbol , int quantity){
		if (quantity < -1){
			System.out.println("quantity of Negative is not an option");
			return false;
		}

		for (int i = 0; i < sizeOfPortfolio; i++){
			if (symbol.equals(stocks[i].getSymbole()))
			{
				if (quantity == -1) {
					updateBalance(stocks[i].getStockQuantity()*stocks[i].getBid()); // sell it All!
					stocks[i].setStockQuantity(0);
					return true;	
				}
				if	(stocks[i].getStockQuantity() < quantity){
					System.out.println("Not enough stocks to sell"); 
				}

				else{
					updateBalance(quantity*(stocks[i].getBid()));// Sell quantity requested
					int newQuantity = (stocks[i].getStockQuantity() - quantity) ;
					stocks[i].setStockQuantity(newQuantity );
					return true;
				}
			}
		}
		System.out.println(" Stock was not found in portfolio");

		
		return false;
	}


	//Buy Stock
	public boolean buyStock (Stock stock , int quantity)
	{
		for (int i = 0; i < sizeOfPortfolio; i++)
		{
			if (stock.getSymbole().equals(stocks[i].getSymbole()))
			{
				isStockExist = i;
				continue;
			}
		}

		// If stock Exist than save it's Index

		int stockNumAfforded = (int)balance/(int)stocks[isStockExist].getAsk();

		if (quantity < -1 || stock == null){
			System.out.println("quantity is Negative or stuck is not vaild");
			return false;
		}

		if (sizeOfPortfolio == MAX_PORTFOLIO_SIZE){
			System.out.println("Portfolio is Full!");
			return false;
		}
		if (isStockExist >= 0)
		{

			if (quantity == -1) {
				if (stockNumAfforded < 1){
					System.out.println("Not enough balance to complete purchase");
					return false;
				}
				updateBalance( - (stockNumAfforded*stocks[isStockExist].getAsk())); // Buy it All!
				int newQuantity = (stocks[isStockExist].getStockQuantity() + stockNumAfforded) ;
				stocks[isStockExist].setStockQuantity(newQuantity );
				return true;
			}

			else{
				if (quantity*stocks[isStockExist].getAsk() > balance){
					System.out.println("Not enough balance to complete purchase");
					return false;
				}
				updateBalance( - (quantity*stocks[isStockExist].getAsk())); // Buy quantity requested
				stocks[isStockExist].setStockQuantity(stocks[isStockExist].getStockQuantity() + quantity); 
				return true;
			}
		}

		if (isStockExist == -1) 
		{
			if (quantity == -1)
			{
				addStock(stock);
				updateBalance(-(stockNumAfforded*stocks[sizeOfPortfolio-1].getAsk()));//  ADD + Buy it All!
				stocks[sizeOfPortfolio-1].setStockQuantity(stocks[sizeOfPortfolio-1].getStockQuantity() + stockNumAfforded);
				return true;
			}

			else
			{
				addStock(stock);
				updateBalance( - (quantity*stocks[sizeOfPortfolio-1].getAsk()));// ADD + Buy quantity requested
				stocks[sizeOfPortfolio-1].setStockQuantity(quantity);
				return true;
			}
		}
		return true;
	}

	//Remove Stock
	public boolean  removeStock(String symbol)
	{
		if (symbol == null){ System.out.println("Stock not vaild"); return false;}


		else if (sizeOfPortfolio == 1 || symbol.equals(stocks[sizeOfPortfolio-1].getSymbole()))	// Last in portfolio
		{
			sellStock(stocks[sizeOfPortfolio-1].getSymbole(), -1);//update Balance
			stocks[sizeOfPortfolio-1] = null;
			sizeOfPortfolio--;
			return true;
		}
		for (int i = 0; i < sizeOfPortfolio; i++) // somewhere in the middle of portfolio
		{

			if (symbol.equals(stocks[i].getSymbole()))
			{
				sellStock(stocks[i].getSymbole(), -1);
				stocks[i] = stocks[sizeOfPortfolio - 1];
				stocks[sizeOfPortfolio - 1] = null;
				sizeOfPortfolio--;
				return true;
			}

		}
		return true;
	}


	// Balance as what's available for investment
	public float getBalance() {
		return balance;
	}


	public void updateBalance(float amount) {
		if((balance+amount) < 0){
			System.out.println("Balance can not be negetive!");
		}

		else{
			this.balance += amount;
		}
	}

	// Value As invested in the portfolio
	public float getStocksValue(){
		float totalValue = 0;
		for (int i = 0; i < sizeOfPortfolio; i++){
			totalValue += stocks[i].getStockQuantity()*stocks[i].getBid();
		}
		return totalValue;
	}

	// All the money in the portfolio combined - the stocks value and the balance
	public float getTotalValue(){
		return (getStocksValue()+getBalance());
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
		return sizeOfPortfolio;
	}

	public void setStockIndex(int stockIndex) {
		this.sizeOfPortfolio = stockIndex;
	}


	public String getHtmlString(){
		String result = new String();
		result = "<h1> " + getTitle() + "</h1>";

		for (int i = 0; i < sizeOfPortfolio; i++)
		{
			Stock presentStock = stocks[i];
			result+= presentStock.getHtmlDescription() + "<br>";
		}
		result += "<b> Portfolio Value : </b>" + getTotalValue() + "$.<br>"
				+"<b> Stocks Value: </b>" + getStocksValue() + "$. <br>" 
				+ "<b> Balance : </b>" + "<u>" +getBalance()+ "</u>" + "$.";
		return result;
	}



}
