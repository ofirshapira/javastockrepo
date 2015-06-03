package com.shapira.model;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

public class Portfolio implements PortfolioInterface {
	public enum ALGO_RECOMMENDATION{
		BUY, SELL, REMOVE, HOLD;
	}

	private final static int MAX_PORTFOLIO_SIZE = 5;
	private String title;
	private StockInterface[] stocks;
	private int sizeOfPortfolio = 0;
	private float balance;

	//c'tor
	public Portfolio() {
		this.stocks = new StockInterface[MAX_PORTFOLIO_SIZE];
		this.title = new String("");	
		this.sizeOfPortfolio = 0;
		this.balance = 0;
	}
	//copy c'tor
	public Portfolio(Portfolio portfolio){
		this.title = new String(portfolio.getTitle());
		this.sizeOfPortfolio = portfolio.getStockIndex();
		this.stocks = new StockInterface[MAX_PORTFOLIO_SIZE];
		for (int i = 0; i < this.sizeOfPortfolio; i++)
		{
			stocks[i] = new Stock(portfolio.getStocks()[i]);
		}
		balance = portfolio.balance;
	}

	public Portfolio(Stock[] stockArr) {
		this.sizeOfPortfolio = 0;
		this.balance = 0;
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.title = null;
		for (int i = 0; i < stockArr.length; i++){
			this.stocks[i] = stockArr[i];
			this.sizeOfPortfolio++;	
		}
	}

	//add Stock
	public void addStock(Stock stock) {
		boolean Exist = false;
		if (stock == null || sizeOfPortfolio >= MAX_PORTFOLIO_SIZE) {
			System.out.println("Error! -> portfolio is full OR Stock is null!");
			return;
		}

		for (int i = 0; i < sizeOfPortfolio; i++) {
			if (stock.getSymbol().equals(stocks[i].getSymbol())) {
				System.out.println("Stock Exists in index "+ stocks[i]+" in portfolio");
				Exist = true;
				return;
			}
		}
		if (Exist == false) {
			if (sizeOfPortfolio < MAX_PORTFOLIO_SIZE) {
				stocks[sizeOfPortfolio] = stock;
				sizeOfPortfolio++;
				return;
			}
		}
	}

	//Sell Stock
	public boolean sellStock (String symbol , int quantity){
		if (quantity < -1){
			System.out.println("quantity of Negative is not an option");
			return false;
		}

		for (int i = 0; i < sizeOfPortfolio; i++){
			if (symbol.equals(stocks[i].getSymbol()))
			{
				if (quantity == -1) {
					updateBalance(((Stock) stocks[i]).getStockQuantity()*stocks[i].getBid()); // sell it All!
					((Stock) stocks[i]).setStockQuantity(0);
					return true;	
				}
				if	(((Stock) stocks[i]).getStockQuantity() < quantity){
					System.out.println("Not enough stocks to sell"); 
				}

				else{
					updateBalance(quantity*(stocks[i].getBid()));// Sell quantity requested
					int newQuantity = (((Stock) stocks[i]).getStockQuantity() - quantity) ;
					((Stock) stocks[i]).setStockQuantity(newQuantity );
					return true;
				}
			}
		}
		System.out.println(" Stock was not found in portfolio");


		return false;
	}

	//Buy Stock
	public boolean buyStock(Stock stock, int quantity) {
		boolean check = false;
		for (int i = 0; i < sizeOfPortfolio; i++) {
			if (stock.getSymbol().equals(stocks[i].getSymbol()))
				check = true;
		}
		if (check == false) {
			addStock(stock);
		}

		for (int i = 0; i < sizeOfPortfolio; i++)
			if (stock.getSymbol().equals(stocks[i].getSymbol())) {
				if (quantity == -1) {
					// buy any quantity you can
					int temp = (int) (balance / stocks[i].getAsk());
					balance = balance - (stocks[i].getAsk() * temp);
					((Stock) stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity()+ temp);
					return true;
				}
				if (balance < stocks[i].getAsk() * quantity) {   // missing money for purchase
					System.out
					.println("Not enough balance to purchase");
					return false;
				} 
				else { 	//buy quantity as requested
					balance = balance - (stocks[i].getAsk() * quantity);   
					((Stock) stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity()+ quantity);
					return true;
				}
			}
		return false;
	}

	//Remove Stock
	public boolean  removeStock(String symbol)
	{
		if (symbol == null){ System.out.println("Stock not vaild"); return false;}

		else if (sizeOfPortfolio == 1 || symbol.equals(stocks[sizeOfPortfolio-1].getSymbol()))	// Last in portfolio
		{
			sellStock(stocks[sizeOfPortfolio-1].getSymbol(), -1);// sell
			stocks[sizeOfPortfolio-1] = null;
			sizeOfPortfolio--;
			return true;
		}
		for (int i = 0; i < sizeOfPortfolio; i++) // somewhere in the middle of portfolio
		{

			if (symbol.equals(stocks[i].getSymbol()))
			{
				sellStock(stocks[i].getSymbol(), -1);
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
			totalValue += ((Stock) stocks[i]).getStockQuantity()*stocks[i].getBid();
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

	public int getStockIndex() {
		return sizeOfPortfolio;
	}

	public void setStockIndex(int stockIndex) {
		this.sizeOfPortfolio = stockIndex;
	}

	@Override
	public StockInterface[] getStocks() {
		return stocks;
	}

	public static int getMaxSize() {

		return Portfolio.MAX_PORTFOLIO_SIZE;
	}

	// Find the Stock in the portfolio
	public StockInterface findStock(String symbol) {
		int i = 0;
		
		if (symbol == null){
			return null;
		}
		for (i = 0; i < this.sizeOfPortfolio; i++){
			if (symbol.equals(this.stocks[i].getSymbol())){
				return this.stocks[i];
			}
		}
		return null; 
		}

	public String getHtmlString(){
		String result = new String();
		result = "<h1> " + getTitle() + "</h1>";

		for (int i = 0; i < sizeOfPortfolio; i++)
		{
			StockInterface presentStock = stocks[i];
			result+= ((Stock) presentStock).getHtmlDescription() + "<br>";
		}
		result += "<b> Portfolio Value : </b>" + getTotalValue() + "$.<br>"
				+"<b> Stocks Value: </b>" + getStocksValue() + "$. <br>" 
				+ "<b> Balance : </b>" + "<u>" +getBalance()+ "</u>" + "$.";
		return result;
	}

}

