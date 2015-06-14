package com.shapira.exception;

import org.algo.exception.PortfolioException;

/**
 * A Full portfolio exception
 */
@SuppressWarnings("serial")
public class PortfolioFullException extends PortfolioException{
	public PortfolioFullException(){
		super("Portfolio is Full");
	}
}