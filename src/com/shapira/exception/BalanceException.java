package com.shapira.exception;

import org.algo.exception.PortfolioException;

/**
 * A negative balance Exception.
 */

@SuppressWarnings("serial")
public class BalanceException extends PortfolioException{
	public BalanceException(){
		super("Balance is negative");
	}
}