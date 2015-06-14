package com.shapira.exception;

import org.algo.exception.PortfolioException;
/**
 * A Selling Try out which is bigger in quantity than what's in balance
 */

@SuppressWarnings("serial")
public class SellingOverQuantityException extends PortfolioException{
	public SellingOverQuantityException(){
		super("Quantity for selling is bigger than available");
	}
}