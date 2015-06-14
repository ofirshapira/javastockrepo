package com.shapira.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;

import com.shapira.exception.BalanceException;
import com.shapira.exception.PortfolioFullException;
import com.shapira.exception.SellingOverQuantityException;
import com.shapira.exception.StockAlreadyExistsException;
import com.shapira.exception.StockNotExistException;
import com.shapira.model.Portfolio;
import com.shapira.model.Stock;

public class PortfolioManager implements PortfolioManagerInterface {

	private DatastoreService datastoreService = ServiceManager.datastoreService();

	public PortfolioInterface getPortfolio() {
		PortfolioDto portfolioDto = datastoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}

	/**
	 * Update portfolio with stocks
	 */
	@Override
	public void update() {
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());
		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());
		List<Stock> currentStocksList = new ArrayList<Stock>();
		try {
			List<StockDto> stocksList = MarketService.getInstance().getStocks(symbols);
			for (StockDto stockDto : stocksList) {
				Stock stock = fromDto(stockDto);
				currentStocksList.add(stock);
			}

			for (Stock stock : currentStocksList) {
				update.add(new Stock(stock));
			}

			datastoreService.saveToDataStore(toDtoList(update));

		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * get portfolio totals
	 */
	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus () {

		Portfolio portfolio = (Portfolio) getPortfolio();
		Map<Date, Float> map = new HashMap<>();

		//get stock status from db.
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			StockInterface stock = stocks[i];

			if(stock != null) {
				List<StockDto> stockHistory = null;
				try {
					stockHistory = datastoreService.getStockHistory(stock.getSymbol(),30);
				} catch (Exception e) {
					return null;
				}
				for (StockDto stockDto : stockHistory) {
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getStockQuantity();

					Date date = stockStatus.getDate();
					Float total = map.get(date);
					if(total == null) {
						total = value;
					}else {
						total += value;
					}
					map.put(date, value);
				}
			}
		}

		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];

		int index = 0;
		//create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}
		//sort by date ascending.
		Arrays.sort(ret);
		return ret;
	}

	/**
	 * Add stock to portfolio 
	 */
	@Override
	public void addStock(String symbol) throws PortfolioException{
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			StockDto stockDto = ServiceManager.marketService().getStock(symbol);
			Stock stock = fromDto(stockDto);
			try {
				portfolio.addStock(stock);
			}  catch (StockAlreadyExistsException e){
				e.getMessage();
				e.printStackTrace();
				throw e;
			}  catch (PortfolioFullException e) {
				e.getMessage();
				e.printStackTrace();
				throw e;
			}
			datastoreService.saveStock(toDto(portfolio.findStock(symbol)));
			flush(portfolio);
		} catch (SymbolNotFoundInNasdaq e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * Buy stock
	 */
	@Override
	public void buyStock(String symbol, int quantity) throws PortfolioException {
		try {
			Portfolio portfolio = (Portfolio) getPortfolio();
			Stock stock = (Stock) portfolio.findStock(symbol);
			if (stock == null) {
				stock = fromDto(ServiceManager.marketService().getStock(symbol));
			}
			portfolio.buyStock(stock, quantity);
			flush(portfolio);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	/**
	 * update database with new portfolio's data
	 * @param portfolio
	 */
	private void flush(Portfolio portfolio) {
		datastoreService.updatePortfolio(toDto(portfolio));
	}

	/**
	 * fromDto - get stock from Data Transfer Object
	 * @param stockDto
	 * @return Stock
	 */
	private Stock fromDto(StockDto stockDto) {
		Stock newStock = new Stock();
		newStock.setSymbol(stockDto.getSymbol());
		newStock.setAsk(stockDto.getAsk());
		newStock.setBid(stockDto.getBid());
		newStock.setDate(stockDto.getDate());
		newStock.setStockQuantity(stockDto.getQuantity());
		if(stockDto.getRecommendation() != null) newStock.setRecommendation(Portfolio.ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));
		else
		{
			newStock.setRecommendation(Portfolio.ALGO_RECOMMENDATION.valueOf("HOLD"));
		}
		return newStock;
	}

	/**
	 * toDto - covert Stock to Stock DTO
	 * @param inStock
	 * @return
	 */
	private StockDto toDto(StockInterface inStock) {
		if (inStock == null) {
			return null;
		}

		Stock stock = (Stock) inStock;
		return new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), 
				stock.getDate(), stock.getStockQuantity(), stock.getRecommendation().name());
	}

	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param portfolio
	 * @return
	 */
	private PortfolioDto toDto(Portfolio portfolio) {
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[portfolio.getStockIndex()];
			for (int i = 0; i < portfolio.getStockIndex(); i++) {
				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}

	/**
	 * fromDto - converts portfolioDto to Portfolio
	 * @param dto
	 * @return portfolio
	 */
	private Portfolio fromDto(PortfolioDto dto) {
		StockDto[] stocks = dto.getStocks();
		Portfolio ret;
		if(stocks == null) {
			ret = new Portfolio();			
		}
		else {
			List<Stock> stockList = new ArrayList<Stock>();
			for (StockDto stockDto : stocks) {
				stockList.add(fromDto(stockDto));
			}

			Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
			ret = new Portfolio(stockArray);
		}

		ret.setTitle(dto.getTitle());
		try {
			ret.updateBalance(dto.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}	

	/**
	 * toDtoList - convert List of Stocks to list of Stock DTO
	 * @param stocks
	 * @return stockDto
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {

		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}	


	/**
	 * A method that returns a new instance of Portfolio copied from another instance.
	 * @param portfolio		Portfolio to copy.
	 * @return a new Portfolio object with the same values as the one given.
	 */
	public Portfolio duplicatePortfolio(Portfolio portfolio) {
		Portfolio copyPortfolio = new Portfolio(portfolio);
		return copyPortfolio;
	}

	/**
	 * Set portfolio title
	 */
	@Override
	public void setTitle(String title) {
		Portfolio portfolio = (Portfolio) getPortfolio();
		portfolio.setTitle(title);
		flush(portfolio);
	}
	/**
	 * Sell stock
	 */
	@Override
	public void sellStock(String symbol, int quantity)
			throws PortfolioException, SellingOverQuantityException, StockNotExistException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			portfolio.sellStock(symbol, quantity);
		} catch (SellingOverQuantityException e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		} catch (StockNotExistException e){
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
		flush(portfolio);
	}

	/**
	 * Remove stock
	 */
	@Override
	public void removeStock(String symbol) throws StockNotExistException, SellingOverQuantityException, BalanceException {
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			portfolio.removeStock(symbol);
		} catch (SellingOverQuantityException e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
		flush(portfolio);
	}

	/**
	 * update portfolio balance
	 */
	public void updateBalance(float value) throws BalanceException { 
		Portfolio portfolio = (Portfolio) getPortfolio();
		try {
			portfolio.updateBalance(value);
		}
		catch (BalanceException e) {
			e.getMessage();
			e.printStackTrace();
			throw e;
		}
		flush(portfolio);
	}
}