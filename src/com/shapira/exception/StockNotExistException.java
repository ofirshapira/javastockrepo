package com.shapira.exception;

import org.algo.exception.PortfolioException;

/**
 * A stock which is not exist.
 */
@SuppressWarnings("serial")
public class StockNotExistException extends PortfolioException{
	public StockNotExistException(){
		super("Stock's not exists");
	}
	public StockNotExistException(String message){
		super(message);
	}
}