package com.shapira.exception;

import org.algo.exception.PortfolioException;
/**
 * A Stock which is already in the porfolio exception.
 */

@SuppressWarnings("serial")
public class StockAlreadyExistsException extends PortfolioException{
	public StockAlreadyExistsException(){
		super("Stock's already exists");
	}
	public StockAlreadyExistsException(String message){
		super(message);
	}
}